package com.example.book.data.di.RepoImpl

import com.example.book.common.BookCategoryModel
import com.example.book.common.BookModel
import com.example.book.common.ResultState
import com.example.book.domain.repo.AllBookRepo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AllBookRepoImpl @Inject constructor(val firebaseDatabase: FirebaseDatabase) : AllBookRepo {

    override fun getAllBooks(): Flow<ResultState<List<BookModel>>> = callbackFlow {
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items: List<BookModel> = snapshot.children
                    .mapNotNull { child ->
                        // safer deserialization
                        child.getValue(BookModel::class.java)
                    }
                trySend(ResultState.Success(items)).isSuccess // optionally check
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }
        }

        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)

        awaitClose{
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }
    }

    override fun getAllCategory(): Flow<ResultState<List<BookCategoryModel>>> = callbackFlow {
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val items: List<BookCategoryModel> = snapshot.children
                    .mapNotNull { child ->
                        // safer deserialization
                        child.getValue(BookCategoryModel::class.java)
                    }
                trySend(ResultState.Success(items)).isSuccess // optionally check
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }

        }
        firebaseDatabase.reference.child("BooksCategory").addValueEventListener(valueEvent)

        awaitClose{
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }
    }

    override fun getAllBooksByCategory(category: String): Flow<ResultState<List<BookModel>>> = callbackFlow {
        trySend(ResultState.Loading)

        println("Category received in getAllBooksByCategory() function: ${category}")

        val valueEvent = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val items: List<BookModel> = snapshot.children
                    .mapNotNull { child -> child.getValue(BookModel::class.java) }
                    .filter { book -> book.category == category }  // <-- filter applied here

                println("Items received after filtering with category: ${items}")

                trySend(ResultState.Success(items)).isSuccess
            }


            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }

        }
        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)

        awaitClose{
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }
    }

    override fun insertBook(book: BookModel): Flow<ResultState<Boolean>> = callbackFlow {
        trySend(ResultState.Loading)

        // create a new unique child under "Books"
        val booksRef = firebaseDatabase.reference.child("Books")
        val newBookRef = booksRef.push() // generates unique key

        // write the book
        newBookRef.setValue(book)
            .addOnSuccessListener {
                // write succeeded
                trySend(ResultState.Success(true)).isSuccess
                // optionally close the flow (if you want single-shot)
                // close()
            }
            .addOnFailureListener { ex ->
                // write failed
                trySend(ResultState.Error(ex)).isSuccess
                // optionally close()
            }

        // keep the flow open until collector cancels; no listeners to remove here
        awaitClose {
            // nothing to remove for a single setValue() call, but keep for symmetry
            close()
        }
    }

    override fun insertCategory(category: BookCategoryModel): Flow<ResultState<Boolean>> = callbackFlow {
        trySend(ResultState.Loading)

        // create a new unique child under "Books"
        val booksCategoryRef = firebaseDatabase.reference.child("BooksCategory")
        val newBookRef = booksCategoryRef.push() // generates unique key

        // write the book
        newBookRef.setValue(category)
            .addOnSuccessListener {
                // write succeeded
                trySend(ResultState.Success(true)).isSuccess
                // optionally close the flow (if you want single-shot)
                // close()
            }
            .addOnFailureListener { ex ->
                // write failed
                trySend(ResultState.Error(ex)).isSuccess
                // optionally close()
            }

        // keep the flow open until collector cancels; no listeners to remove here
        awaitClose {
            // nothing to remove for a single setValue() call, but keep for symmetry
            close()
        }
    }

}
