package com.nekretnineferit.viewmodel.browsing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekretnineferit.firebase.getHouses

class HomeViewModel: ViewModel() {
    init {
        doGetHouses()
    }

    fun doGetHouses() {
        getHouses(viewModelScope)
    }
}