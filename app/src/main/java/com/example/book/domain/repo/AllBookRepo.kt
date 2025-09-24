package com.example.book.domain.repo

import com.example.book.common.BookCategoryModel
import com.example.book.common.BookModel
import com.example.book.common.ResultState
import kotlinx.coroutines.flow.Flow

interface AllBookRepo {
    fun getAllBooks(): Flow<ResultState<List<BookModel>>>
    fun getAllCategory(): Flow<ResultState<List<BookCategoryModel>>>
    fun getAllBooksByCategory(category: String): Flow<ResultState<List<BookModel>>>
    fun insertBook(book: BookModel) : Flow<ResultState<Boolean>>
    fun insertCategory(category: BookCategoryModel) : Flow<ResultState<Boolean>>
}