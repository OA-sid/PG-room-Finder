package com.example.renthostelfinder.ui.theme.viewModels


import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileUpdateViewModel : ViewModel() {

    fun updateProfile(email: String, phoneNumber: String, studentID: String, gender: String, onResult: (Result<Boolean>) -> Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val user = hashMapOf(
            "email" to email,
            "phoneNumber" to phoneNumber,
            "studentID" to studentID,
            "gender" to gender
        )
        FirebaseFirestore.getInstance().collection("users")
            .document(currentUser!!.uid)
            .set(user)
            .addOnSuccessListener {
                onResult(Result.success(true))
            }
            .addOnFailureListener {
                onResult(Result.failure(it))
            }
    }
}
