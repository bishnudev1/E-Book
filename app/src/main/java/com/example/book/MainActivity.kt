package com.example.book

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.book.presentation.navigation.NavGraph
import com.example.book.ui.theme.BookTheme
import com.example.book.presentation.splash.SplashScreen
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            BookTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navHostController = rememberNavController()
    val showsSplash = remember { mutableStateOf(true) }
    val startDestination = remember { mutableStateOf("") }

    // Use LaunchedEffect + delay (no Handler)
    LaunchedEffect(Unit) {
        // show splash for 2 seconds
        delay(2000)

        // decide start destination by checking FirebaseAuth synchronously
        val currentUser = FirebaseAuth.getInstance().currentUser
        startDestination.value = if (currentUser != null) {
            com.example.book.presentation.navigation.Routes.Home.route
        } else {
            com.example.book.presentation.navigation.Routes.Login.route
        }

        showsSplash.value = false
    }

    if (showsSplash.value) {
        SplashScreen()
    } else {
        // pass dynamic startDestination to NavGraph
        NavGraph(navHostController = navHostController, startDestination = startDestination.value)
    }
}
