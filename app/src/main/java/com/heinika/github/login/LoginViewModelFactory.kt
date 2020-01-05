package com.heinika.github.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(private var loginRepository: LoginRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(loginRepository) as T
    }

}