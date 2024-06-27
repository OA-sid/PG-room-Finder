// PostAdViewModel.kt
package com.example.renthostelfinder.ui.theme.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.renthostelfinder.ui.theme.datas.HouseData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class PostAdViewModel : ViewModel() {
    private val _house = MutableStateFlow(HouseData())
    val house: StateFlow<HouseData> = _house

    fun updateHouse(updatedHouse: HouseData) {
        _house.value = updatedHouse
    }

    fun saveHouseToFirebase(imageUri: Uri?, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                if (imageUri != null) {
                    val storageRef = FirebaseStorage.getInstance().reference.child("images/${UUID.randomUUID()}")
                    storageRef.putFile(imageUri).addOnSuccessListener {
                        storageRef.downloadUrl.addOnSuccessListener { uri ->
                            saveHouseToFirestore(uri.toString(), onSuccess, onFailure)
                        }.addOnFailureListener {
                            onFailure(it)
                        }
                    }.addOnFailureListener {
                        onFailure(it)
                    }
                } else {
                    saveHouseToFirestore(null, onSuccess, onFailure)
                }
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    private fun saveHouseToFirestore(imageUrl: String?, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val houseData = _house.value.copy(imageUrl = imageUrl ?: "")
        FirebaseFirestore.getInstance().collection("houses")
            .add(houseData)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }
}
