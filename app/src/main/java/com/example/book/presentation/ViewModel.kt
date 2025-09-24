package com.example.book.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.book.common.BookCategoryModel
import com.example.book.common.BookModel
import com.example.book.common.ResultState
import com.example.book.domain.repo.AllBookRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ViewModel @Inject constructor(val repo: AllBookRepo) : ViewModel() {
    private val _state : MutableState<ItemState> = mutableStateOf(ItemState())
    val state : MutableState<ItemState> = _state

    fun BringAllBooks(){
        viewModelScope.launch {
            repo.getAllBooks().collect {
                when(it){
                    is ResultState.Loading -> {
                        _state.value = ItemState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _state.value = ItemState(items = it.data)
                    }
                    is ResultState.Error -> {
                        _state.value = ItemState(error = it.exception.localizedMessage)
                    }
                }
            }
        }
    }

    fun BringAllCategories(){
        viewModelScope.launch {
            repo.getAllCategory().collect {
                when(it){
                    is ResultState.Loading -> {
                        _state.value = ItemState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _state.value = ItemState(category = it.data)
                    }
                    is ResultState.Error -> {
                        _state.value = ItemState(error = it.exception.localizedMessage)
                    }
                }
            }
        }
    }

    fun BringBookByCategory(category: String){
        viewModelScope.launch {
            repo.getAllBooksByCategory(category).collect {
                when(it){
                    is ResultState.Loading -> {
                        _state.value = ItemState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _state.value = ItemState( items = it.data)
                    }
                    is ResultState.Error -> {
                        _state.value = ItemState(error = it.exception.localizedMessage)
                    }
                }
            }
        }
    }

    fun insertBook(book: BookModel, context: Context, navHostController: NavHostController) {
        viewModelScope.launch {
            repo.insertBook(book).collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _state.value = ItemState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _state.value = ItemState(item = result.data, isLoading = false)
                        Toast.makeText(context, "Book added successfully!", Toast.LENGTH_SHORT).show()
                        navHostController.popBackStack() // go back after success
                    }
                    is ResultState.Error -> {
                        _state.value = ItemState(error = result.toString(), isLoading = false)
                        Toast.makeText(context, "Error: ${result.toString()}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun insertBookCategory(category: BookCategoryModel, context: Context, navHostController: NavHostController) {
        viewModelScope.launch {
            repo.insertCategory(category).collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _state.value = ItemState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _state.value = ItemState(item = result.data, isLoading = false)
                        Toast.makeText(context, "Book category added successfully!", Toast.LENGTH_SHORT).show()
                        navHostController.popBackStack() // go back after success
                    }
                    is ResultState.Error -> {
                        _state.value = ItemState(error = result.toString(), isLoading = false)
                        Toast.makeText(context, "Error: ${result.toString()}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

data class ItemState(
    val isLoading : Boolean = false,
    val items : List<BookModel> = emptyList(),
    val error : String = "",
    val item: Boolean? = null,
    val category : List<BookCategoryModel> = emptyList()
)