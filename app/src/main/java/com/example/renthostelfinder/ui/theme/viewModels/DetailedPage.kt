package com.example.renthostelfinder.ui.theme.viewModels

import androidx.lifecycle.ViewModel
import com.example.renthostelfinder.ui.theme.datas.HouseData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailPageViewModel : ViewModel() {
    private val _house = MutableStateFlow<HouseData?>(null)
    val house: StateFlow<HouseData?> get() = _house

    fun setHouse(house: HouseData) {
        _house.value = house
    }
}
