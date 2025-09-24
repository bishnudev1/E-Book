package com.example.book.data.di.RepoImpl

import com.example.book.common.ResultState
import com.example.book.common.UserModel
import com.example.book.domain.repo.AllAuthRepo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth


class AllAuthRepoImpl @Inject constructor(val firebaseAuth: FirebaseAuth) : AllAuthRepo {

    override fun userLogin(userModel: UserModel): Flow<ResultState<UserModel>> = callbackFlow {
        // signal loading
        trySend(ResultState.Loading)

        val auth = FirebaseAuth.getInstance()
        val email = userModel.email.orEmpty()
        val password = userModel.password.orEmpty()

        if (email.isEmpty() || password.isEmpty()) {
            trySend(ResultState.Error(IllegalArgumentException("Email or password empty")))
            // close the flow if you want single-shot behavior
            // close()
            awaitClose { /* no-op */ }
            return@callbackFlow
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val firebaseUser = authResult.user
                if (firebaseUser != null) {
                    // create a new UserModel (copy) populated from Firebase user info
                    // adjust fields according to your UserModel definition
                    val loggedInUser = userModel.copy(
                        uid = firebaseUser.uid,
                        email = firebaseUser.email ?: email,
                    )
                    trySend(ResultState.Success(loggedInUser))
                    // If you want the flow to complete after one result, close it:
                    // close()
                } else {
                    trySend(ResultState.Error(Exception("Authentication succeeded but user is null")))
                }
            }
            .addOnFailureListener { ex ->
                trySend(ResultState.Error(ex))
            }

        // keep the flow open until the collector cancels; no listener to remove for Task
        awaitClose {
            // If you registered any listeners you would remove them here.
            // For signInWithEmailAndPassword() the Task listeners can't be removed,
            // so just do nothing. If you prefer single-shot semantics, uncomment `close()` above.
        }
    }

    override fun userLogout(): Flow<ResultState<Boolean>> = callbackFlow {
        trySend(ResultState.Loading)

        FirebaseAuth.getInstance().signOut()

        trySend(ResultState.Success(true))

        awaitClose {
        }
        }

    override fun userRegister(userModel: UserModel): Flow<ResultState<Boolean>> = callbackFlow {
        trySend(ResultState.Loading)
        val auth = FirebaseAuth.getInstance()
        val email = userModel.email.orEmpty()
        val password = userModel.password.orEmpty()
        if (email.isEmpty() || password.isEmpty()) {
            trySend(ResultState.Error(IllegalArgumentException("Email or password empty")))
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val firebaseUser = authResult.user
                if (firebaseUser != null)
                    trySend(ResultState.Success(true))
                else
                    trySend(ResultState.Error(Exception("Authentication succeeded but user is null")))
            }
            .addOnFailureListener { ex ->
                trySend(ResultState.Error(ex))
            }
        awaitClose {
        }

    }
    }
