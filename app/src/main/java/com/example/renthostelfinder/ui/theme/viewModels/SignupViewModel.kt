package com.example.renthostelfinder.ui.theme.viewModels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpViewModel : ViewModel() {

    fun signUp(email: String, password: String, phoneNumber: String, studentID: String, gender: String, userType: String, onResult: (Result<Boolean>) -> Unit) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = hashMapOf(
                        "email" to email,
                        "phoneNumber" to phoneNumber,
                        "studentID" to studentID,
                        "gender" to gender,
                        "userType" to userType
                    )
                    FirebaseFirestore.getInstance().collection("users")
                        .document(FirebaseAuth.getInstance().currentUser!!.uid)
                        .set(user)
                        .addOnSuccessListener {
                            onResult(Result.success(true))
                        }
                        .addOnFailureListener {
                            onResult(Result.failure(it))
                        }
                } else {
                    onResult(Result.failure(task.exception!!))
                }
            }
    }
}
