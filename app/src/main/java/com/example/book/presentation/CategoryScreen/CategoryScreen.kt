package com.example.book.presentation.CategoryScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.book.presentation.ViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.book.presentation.Effects.CategoryShimmer
import com.example.book.presentation.UIComponent.BookCategoryCard

@Composable
fun CategoryScreen(
    viewModel: ViewModel = hiltViewModel(),
    navHostController: NavHostController) {

    LaunchedEffect(Unit) {
        println("Viewmodel called of CategoryScreen")
        viewModel.BringAllCategories()
    }
    Column (modifier = Modifier.fillMaxSize()){
        val res = viewModel.state.value

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

            res.category.isNotEmpty() -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(res.category.size) { index ->
                        val cat = res.category[index]
                        BookCategoryCard(
                            navHostController = navHostController,
                            category = cat.name,
                            imageUrl = cat.categoryImageUrl
                        )
                    }

                }
            }



        }
    }
}