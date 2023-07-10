package com.example.mentalhealthapp.model

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

object FirebaseModel {

    private var firebaseAuth =FirebaseAuth.getInstance()
    private var user = firebaseAuth.currentUser






    suspend fun loginWithEmailAndPassword(email:String,password:String):Pair<Boolean,String>{

        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val result = Pair<Boolean,String>(true,"Logged In...")
            result
        }
        catch (e:Exception){
            val result = Pair<Boolean,String>(false,e.message.toString())
            result
        }
    }
    fun logoutUser(){
        firebaseAuth.signOut()
        user = null
    }


    suspend fun signUpWithEmailAndPassword(email:String,password:String):Pair<Boolean,String>{

        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val result = Pair<Boolean,String>(true,"Logged In...")
            result
        }
        catch (e:Exception){
            val result = Pair<Boolean,String>(false,e.message.toString())
            result
        }
    }

    suspend fun signUpWithGoogle(account: GoogleSignInAccount):Pair<Boolean,String>{
        val credentials = GoogleAuthProvider.getCredential(account.idToken ,null)
        return try {
            firebaseAuth.signInWithCredential(credentials).await()
            val result = Pair(true,"SignIn successful...")
            updateCredentials()
            result
        }catch (e:Exception){
            val result = Pair(false,e.message.toString())
            result
        }

    }

    private fun updateCredentials() {
        user = firebaseAuth.currentUser
    }

    fun checkUserStatus(moveToHome: () -> Unit) {
        firebaseAuth.currentUser?.let {
            moveToHome.invoke()
        }
    }

}