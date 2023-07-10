package com.example.mentalhealthapp.model

import com.example.mentalhealthapp.dataclasses.MoodItem
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object FirebaseModel {

    private var firebaseAuth =FirebaseAuth.getInstance()
    private var user = firebaseAuth.currentUser
    private  val calender=Calendar.getInstance()








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

    suspend fun addUserMoodItem(userMoodItem: MoodItem):Pair<Boolean,String>{
       val UID = firebaseAuth.currentUser?.uid
        val year= calender.get( Calendar.YEAR)
        val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
        val month = monthFormat.format(calender.time)

        val refPath = "/mental_health_app/UserRecords/$UID/MoodRecords/$month$year"
        val recordRef = Firebase.firestore.collection(refPath)

        return  try {
            recordRef.add(userMoodItem).await()
            val resultPair = Pair(true, "Mood updated...")
            resultPair
        }
        catch (e:Exception){
            val resultPair = Pair(false,e.message.toString())
            resultPair

        }
    }
}