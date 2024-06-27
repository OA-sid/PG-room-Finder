package com.example.renthostelfinder.ui.theme.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.renthostelfinder.ui.theme.datas.HouseData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomePageViewModel : ViewModel() {
    private val _houses = MutableStateFlow<List<HouseData>>(emptyList())
    val houses: StateFlow<List<HouseData>> get() = _houses

    init {
        loadHouses()
    }

    private fun loadHouses() {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            db.collection("houses")
                .get()
                .addOnSuccessListener { result ->
                    val houseList = result.map { it.toObject(HouseData::class.java) }
                    _houses.value = houseList
                }
                .addOnFailureListener {
                    // Handle the error
                }
        }
    }
}