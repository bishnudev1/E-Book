package com.example.book.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.book.common.ResultState
import com.example.book.common.UserModel
import com.example.book.domain.repo.AllAuthRepo
import com.example.book.presentation.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(val repo: AllAuthRepo) : ViewModel() {
    private val _state: MutableState<AuthState> = mutableStateOf(AuthState())
    val state: MutableState<AuthState> = _state

    fun loginUser(userModel: UserModel, context: Context, navHostController: NavHostController) {
        viewModelScope.launch {
            repo.userLogin(userModel).collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _state.value = AuthState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _state.value = AuthState(user = result.data, isLoading = false)
                        Toast.makeText(context, "User login successfully!", Toast.LENGTH_SHORT)
                            .show()
//                        navHostController.popBackStack() // go back after success
                        navHostController.navigate(Routes.Home.route)
                    }

                    is ResultState.Error -> {
                        _state.value = AuthState(error = result.toString(), isLoading = false)
                        Toast.makeText(context, "Error: ${result.toString()}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    fun logoutUser(context: Context, navHostController: NavHostController) {
        viewModelScope.launch {
            repo.userLogout().collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _state.value = AuthState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _state.value = AuthState(
                            user = UserModel(),
                            isLoading = false)
                        Toast.makeText(
                            context,
                            "User logout successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                        navHostController.navigate(Routes.Login.route
                        )
                    }

                    is ResultState.Error -> {
                        _state.value = AuthState(error = result.toString(), isLoading = false)
                        Toast.makeText(context, "Error: ${result.toString()}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        }

    }

    fun registerUser(userModel: UserModel, context: Context, navHostController: NavHostController){
        viewModelScope.launch {
            repo.userRegister(userModel).collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _state.value = AuthState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _state.value = AuthState( isLoading = false)
                        Toast.makeText(context, "User register successfully!", Toast.LENGTH_SHORT)
                            .show()

                        navHostController.navigate(Routes.Login.route)
                    }
                    is ResultState.Error -> {
                        _state.value = AuthState(error = result.toString(), isLoading = false)
                        Toast.makeText(context, "Error: ${result.toString()}", Toast.LENGTH_SHORT)
                            .show()
                    }
                    }
            }
        }
    }
}

data class AuthState(
    val user : UserModel = UserModel(),
    val isLoading : Boolean = false,
    val error : String = "",
)