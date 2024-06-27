// MyAdsViewModel.kt
package com.example.renthostelfinder.ui.theme.viewModels

import androidx.lifecycle.ViewModel
import com.example.renthostelfinder.ui.theme.datas.HouseData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyAdsViewModel : ViewModel() {

    private val _ads = MutableStateFlow<List<HouseData>>(emptyList())
    val ads: StateFlow<List<HouseData>> = _ads

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    init {
        fetchUserAds()
    }

    private fun fetchUserAds() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firestore.collection("houses")
                .whereEqualTo("ownerId", userId)
                .get()
                .addOnSuccessListener { result ->
                    val adsList = result.map { document ->
                        val house = document.toObject(HouseData::class.java)
                        house.copy(id = document.id)
                    }
                    _ads.value = adsList
                }
                .addOnFailureListener { exception ->
                    // Handle error
                }
        }
    }

    fun deleteAd(adId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("houses").document(adId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}
