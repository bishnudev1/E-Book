package com.example.book.presentation.CreateBookScreen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.book.common.BookModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.book.presentation.ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBookScreen(
    navHostController: NavHostController,
    viewModel: ViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.value

    var name by remember { mutableStateOf(TextFieldValue("")) }
    var author by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var category by remember { mutableStateOf(TextFieldValue("")) }
    var url by remember { mutableStateOf(TextFieldValue("")) }
    var image by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Book") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Book Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Author") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = url,
                onValueChange = { url = it },
                label = { Text("Book URL") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = image,
                onValueChange = { image = it },
                label = { Text("Image URL") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if(name.text.isEmpty() || author.text.isEmpty() || description.text.isEmpty() || category.text.isEmpty() || url.text.isEmpty()){
                        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                        return@Button

                    }
                    val book = BookModel(
                        bookName = name.text,
                        bookAuthor = author.text,
                        bookDescription = description.text,
                        category = category.text,
                        bookUrl = url.text,
                        image = image.text// skipping image for now
                    )
                    viewModel.insertBook(book, context, navHostController)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit Book")
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}
