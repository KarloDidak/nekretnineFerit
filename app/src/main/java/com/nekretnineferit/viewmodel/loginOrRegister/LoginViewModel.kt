package com.nekretnineferit.viewmodel.loginOrRegister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekretnineferit.firebase.userLogin

class LoginViewModel : ViewModel() {
    fun doUserLogin(email: String, password: String) {
        userLogin(viewModelScope, email, password)
    }
}