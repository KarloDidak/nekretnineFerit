package com.nekretnineferit.viewmodel.browsing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekretnineferit.firebase.deleteHouse
import com.nekretnineferit.data.House

class HouseDetailsViewModel: ViewModel() {
    fun doDeleteHouse(house: House) {
        deleteHouse(viewModelScope, house)
    }
}