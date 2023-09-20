package com.nekretnineferit.viewmodel.loginOrRegister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekretnineferit.firebase.userGetCurrent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class StartViewModel: ViewModel() {
    private val _navigate = MutableSharedFlow<Int>()
    val navigate = _navigate.asSharedFlow()

    companion object{
        const val SHOW_START_BUTTON = 1
        const val GO_TO_BROWSING_ACTIVITY = 2
    }

    init {
        viewModelScope.launch {
            val currUser = userGetCurrent()
            if (currUser == null) {
                _navigate.emit(SHOW_START_BUTTON)
            } else {
                _navigate.emit(GO_TO_BROWSING_ACTIVITY)
            }
        }
    }
}