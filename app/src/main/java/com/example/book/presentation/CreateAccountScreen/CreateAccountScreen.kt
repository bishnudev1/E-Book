package com.example.book.presentation.CreateAccountScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.book.common.UserModel
import com.example.book.presentation.AuthViewModel
import com.example.book.presentation.ViewModel
import com.example.book.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen(navHostController: NavHostController,authViewModel: AuthViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val state = authViewModel.state.value
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Create Account") },
            )
        }
    ){
        padding ->
        Column (
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Row (     modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically){
                Text("Already have an account?")
                TextButton(
                    onClick = {
                        navHostController.navigate(Routes.Login.route)
                    }
                ) {
                    Text("Login")
                }
            }
            Button(
                onClick = {
                    if (state.isLoading) {
                        return@Button
                    }
                    if (email.text.isEmpty() || password.text.isEmpty()) {
                        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                    } else {
                        // Call your function here
                        // viewModel.createAccount(email.text, password.text, context, navHostController)
                        val data = UserModel(email = email.text, password = password.text)
                        authViewModel.registerUser(
                      data,
                            context,
                            navHostController
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp) // adjust size so it fits inside button
                    )
                } else {
                    Text("Create Account")
                }
            }

        }
    }

}