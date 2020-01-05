package com.heinika.github.login


import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel(private var loginRepository: LoginRepository) : ViewModel() {
    var _usernameError: MutableLiveData<String> = MutableLiveData("")
    var _passwordError: MutableLiveData<String> = MutableLiveData("")
    var _email: MutableLiveData<String> = MutableLiveData("")
    var usernameError: LiveData<String> = _usernameError
    var passwordError: LiveData<String> = _passwordError
    var email: LiveData<String> = _email

    fun login(userName: Editable?, password: Editable?) {
        if (userName.isNullOrEmpty()) {
            _usernameError.value = "please input your username!"
            return
        }

        if (password.isNullOrEmpty()) {
            _passwordError.value = "please input your password!"
            return
        }

        loginRepository.login(userName.toString(), password.toString(), this)
    }

    fun onUsernameTextChange(text: Editable?) {
        _usernameError.value = null
    }

    fun onPasswordTextChange(s: Editable?) {
        _passwordError.value = null
    }
}