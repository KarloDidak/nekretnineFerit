package com.nekretnineferit.viewmodel.loginOrRegister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekretnineferit.firebase.userRegister

class RegisterViewModel: ViewModel() {
    fun doUserRegister(name: String, email: String, password: String) {
        userRegister(viewModelScope, name, email, password)
    }
}