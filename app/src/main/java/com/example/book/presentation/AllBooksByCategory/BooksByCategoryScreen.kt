package com.example.book.presentation.AllBooksByCategory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.book.presentation.Effects.CategoryShimmer
import com.example.book.presentation.ViewModel
import com.example.book.presentation.uicomponent.BookCart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksByCategoryScreen(viewModel: ViewModel = hiltViewModel(), modifier: Modifier,category: String, navHostController: NavHostController) {
    LaunchedEffect(Unit) {
        println("Category received: ${category}")
        viewModel.BringBookByCategory(category)
    }

    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (modifier = Modifier.fillMaxSize().nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(category) },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
        ){
        innerpadding ->
        val res = viewModel.state.value

        Column (modifier = Modifier.fillMaxSize().padding((innerpadding))){
            when{
                res.isLoading -> {
                    Column (modifier = Modifier.fillMaxSize()){
                        LazyVerticalGrid(
                            GridCells.Fixed(2), modifier = Modifier.fillMaxSize()
                        ) {
                            items (10){
                                CategoryShimmer()
                            }
                        }
                    }
                }

                res.error.isNotEmpty() -> {
                    Text(text = res.error)
                }

                res.items.isNotEmpty() -> {
                    Column (modifier = Modifier.fillMaxSize()){
                        LazyColumn (modifier = Modifier.fillMaxSize()){
                            items (res.items.size){
                                val book = res.items[it]
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
                } else -> {
                    Text(text = "No books in this category!")
                }



            }
        }
    }

}