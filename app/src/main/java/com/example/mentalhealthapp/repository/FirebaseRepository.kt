package com.example.mentalhealthapp.repository

import androidx.lifecycle.LiveData
import com.example.mentalhealthapp.dataclasses.MoodItem
import com.example.mentalhealthapp.model.FirebaseInterface
import com.example.mentalhealthapp.model.FirebaseModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

object FirebaseRepository:FirebaseInterface {


    private val modelRef = FirebaseModel

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Pair<Boolean, String> {
        return modelRef.loginWithEmailAndPassword(email, password)
    }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ): Pair<Boolean, String> {
        return  modelRef.signUpWithEmailAndPassword(email, password)
    }

    override suspend fun signInWithGoogle(): Pair<Boolean, String> {
        //TODO("Not yet implemented")
        val result = Pair<Boolean,String>(false,"haha")
        return result
    }

    override fun logoutUser() {
        modelRef.logoutUser()
    }

    override suspend fun signUpWithGoogle(account: GoogleSignInAccount): Pair<Boolean, String> {
        return modelRef.signUpWithGoogle(account)
    }

    override fun checkUserStatus(moveToHome: () -> Unit) {
        modelRef.checkUserStatus(moveToHome)
    }

    override suspend fun addUserMoodItem(userMoodItem: MoodItem): Pair<Boolean, String> {
        return modelRef.addUserMoodItem(userMoodItem)

    }

    override suspend fun getTodaysMood():Pair<Boolean,MoodItem?>{
        return modelRef.getTodaysMood()
    }
    override suspend fun updateUserMoodItem(updateMap: HashMap<String,Any>,oldMoodItem: MoodItem):Pair<Boolean,String>{
        return modelRef.updateUserMoodItem(updateMap,oldMoodItem)
    }

    override fun getTheDatabase() {
         modelRef.getTheDatabase()
    }

    override fun getRecordLiveData(): LiveData<ArrayList<MoodItem>> {
        return modelRef.getRecordLiveData()
    }
    suspend fun deleteprevRecord(){
        modelRef.deletePreviousMonthRecord()
    }


}