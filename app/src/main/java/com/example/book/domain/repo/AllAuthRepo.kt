package com.example.book.domain.repo
import com.example.book.common.ResultState
import com.example.book.common.UserModel
import kotlinx.coroutines.flow.Flow

interface AllAuthRepo {
    fun userLogin(userModel: UserModel): Flow<ResultState<UserModel>>
    fun userLogout(): Flow<ResultState<Boolean>>
}