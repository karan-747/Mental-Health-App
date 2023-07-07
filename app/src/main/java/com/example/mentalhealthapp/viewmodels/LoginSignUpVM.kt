package com.example.mentalhealthapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mentalhealthapp.repository.FirebaseRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class LoginSignUpVM: ViewModel() {


    val repoRef = FirebaseRepository


     suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ): Pair<Boolean, String> {
        return  repoRef.signUpWithEmailAndPassword(email, password)
    }

    suspend fun logInWithEmailAndPassword(
        email: String,
        password: String
    ): Pair<Boolean, String> {
        return  repoRef.loginWithEmailAndPassword(email, password)
    }

    fun logoutUser(){
        repoRef.logoutUser()
    }

    suspend fun signInWithGoogle(account: GoogleSignInAccount): Pair<Boolean,String> {
        return repoRef.signUpWithGoogle(account)
    }

    fun checkLoginStatus(moveToHome: () -> Unit) {
        repoRef.checkUserStatus(moveToHome)
    }

}