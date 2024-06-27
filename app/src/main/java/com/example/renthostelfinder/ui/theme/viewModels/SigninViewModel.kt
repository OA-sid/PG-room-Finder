// SignInViewModel.kt
package com.example.renthostelfinder.ui.theme.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun signIn(email: String, password: String, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            db.collection("users").document(userId).get()
                                .addOnSuccessListener { document ->
                                    val userType = document.getString("userType")
                                    if (userType != null) {
                                        onSuccess(userType)
                                    } else {
                                        onFailure(Exception("User type not found"))
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    onFailure(exception)
                                }
                        } else {
                            onFailure(Exception("User ID not found"))
                        }
                    } else {
                        onFailure(task.exception ?: Exception("Sign-in failed"))
                    }
                }
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}
