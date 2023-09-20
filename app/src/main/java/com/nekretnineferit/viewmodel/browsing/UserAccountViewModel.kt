package com.nekretnineferit.viewmodel.browsing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekretnineferit.firebase.userUpdate

class UserAccountViewModel: ViewModel() {
    fun doUserUpdate(name: String, email: String, imagePath: String) {
        userUpdate(viewModelScope, name, email, imagePath)
    }
}