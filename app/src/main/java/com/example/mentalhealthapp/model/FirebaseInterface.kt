package com.example.mentalhealthapp.model

import com.example.mentalhealthapp.dataclasses.MoodItem
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface FirebaseInterface {


    suspend fun loginWithEmailAndPassword(email:String,password:String):Pair<Boolean,String>
    suspend fun signUpWithEmailAndPassword(email:String,password:String):Pair<Boolean,String>
    suspend fun signInWithGoogle():Pair<Boolean,String>

    fun logoutUser():Unit

    suspend fun signUpWithGoogle(account: GoogleSignInAccount):Pair<Boolean,String>
    fun checkUserStatus(moveToHome: () -> Unit)

    suspend fun addUserMoodItem(userMoodItem: MoodItem) :Pair<Boolean,String>
}