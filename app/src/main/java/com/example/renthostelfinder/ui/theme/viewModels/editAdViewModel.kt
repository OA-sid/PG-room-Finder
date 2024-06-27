// EditAdViewModel.kt
package com.example.renthostelfinder.ui.theme.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.renthostelfinder.ui.theme.datas.HouseData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditAdViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _ad = MutableStateFlow<HouseData?>(null)
    val ad: StateFlow<HouseData?> = _ad

    fun getAd(adId: String) {
        db.collection("ads").document(adId).get().addOnSuccessListener { document ->
            _ad.value = document.toObject(HouseData::class.java)
        }.addOnFailureListener {
            _ad.value = null
        }
    }

    fun updateHouse(updatedAd: HouseData, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("ads").document(updatedAd.id).set(updatedAd)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }
}
