package com.example.book.presentation.navigation

import android.net.Uri

sealed class Routes(val route: String) {
    object Home : Routes("home")

    object Login : Routes("login")

    object Register : Routes("register")

    object InsertBook : Routes("insertBook")

    // path-parameter style: "books/{category}"
    object BooksByCategory : Routes("books/{category}") {
        fun createRoute(category: String) = "books/${Uri.encode(category)}"
    }

    // query-parameter style: "showPdf?url={url}"
    object ShowPdf : Routes("showPdf?url={url}") {
        fun createRoute(url: String): String {
            // encode the url so slashes etc. don't break the route
            return "showPdf?url=${Uri.encode(url)}"
        }
    }
}
