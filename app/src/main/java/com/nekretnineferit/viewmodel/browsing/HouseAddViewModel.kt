package com.nekretnineferit.viewmodel.browsing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekretnineferit.firebase.addHouse
import com.nekretnineferit.data.House

class HouseAddViewModel: ViewModel() {
    fun doAddHouse(house: House) {
        addHouse(viewModelScope, house)
    }
}