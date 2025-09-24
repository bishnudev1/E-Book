package com.example.book.presentation.AllBooksScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.book.presentation.ViewModel
import com.example.book.presentation.Effects.CategoryShimmer
import com.example.book.presentation.uicomponent.BookCart

@Composable
fun AllBooksScreen(viewModel: ViewModel = hiltViewModel(), modifier: Modifier, navHostController: NavHostController) {

    LaunchedEffect(Unit) {
        viewModel.BringAllBooks()
    }

    val res = viewModel.state.value

    when {
        res.isLoading -> {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn {
                    items(10) {
                        CategoryShimmer()
                    }
                }
            }
        }

        res.error.isNotEmpty() -> {
            Text(text = res.error, modifier = modifier)
        }

        res.items.isNotEmpty() -> {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(res.items.size) { index ->
                        val book = res.items[index]
                        BookCart(
                            navHostController = navHostController,
                            imageUrl = book.image,
                            title = book.bookName,
                            author = book.bookAuthor,
                            description = book.bookDescription,
                            bookUrl = book.bookUrl
                        )
                    }
                }
            }
        }else -> Text(text = "No books available...", modifier = modifier)
    }
}