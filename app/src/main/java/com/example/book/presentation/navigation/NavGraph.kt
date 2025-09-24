package com.example.book.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.book.presentation.AllBooksByCategory.BooksByCategoryScreen
import com.example.book.presentation.CreateAccountScreen.CreateAccountScreen
import com.example.book.presentation.CreateBookScreen.CreateBookScreen
import com.example.book.presentation.LoginScreen.LoginScreen
import com.example.book.presentation.UIComponent.PDFViewerScreen
import com.example.book.presentation.homescreen.HomeScreen

@Composable
fun NavGraph(
    navHostController: androidx.navigation.NavHostController,
    startDestination: String = Routes.Home.route
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(Routes.Home.route) {
            HomeScreen(navHostController = navHostController)
        }
        composable(Routes.Login.route) {
            LoginScreen(navHostController = navHostController)
        }
        composable(Routes.Register.route) {
            CreateAccountScreen(navHostController = navHostController)
        }
        composable(Routes.InsertBook.route) {
            CreateBookScreen(navHostController = navHostController)
        }

        composable(
            route = Routes.BooksByCategory.route,
            arguments = listOf(
                navArgument("category") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")?.let { Uri.decode(it) } ?: ""
            BooksByCategoryScreen(category = category, navHostController = navHostController, modifier = Modifier)
        }

        composable(
            route = Routes.ShowPdf.route,
            arguments = listOf(
                navArgument("url") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("url") ?: ""
            val url = Uri.decode(encodedUrl)
            PDFViewerScreen(url = url)
        }
    }
}
