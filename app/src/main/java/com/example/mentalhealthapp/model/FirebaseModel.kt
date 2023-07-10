package com.example.mentalhealthapp.model

import com.example.mentalhealthapp.dataclasses.MoodItem
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.SetOptions
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


    suspend fun updateUserMoodItem(updateMap: HashMap<String,Any>,oldMoodItem: MoodItem):Pair<Boolean,String>{
        val UID = firebaseAuth.currentUser?.uid
        val year= calender.get( Calendar.YEAR)
        val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
        val month = monthFormat.format(calender.time)

        val refPath = "/mental_health_app/UserRecords/$UID/MoodRecords/$month$year"
        val recordRef = Firebase.firestore.collection(refPath)

        val recordQuery = recordRef
            .whereEqualTo("date" , oldMoodItem.date)
            .whereEqualTo("day" , oldMoodItem.day)
            .whereEqualTo("empty" , oldMoodItem.empty)
            .whereEqualTo("month" , oldMoodItem.month)
            .whereEqualTo("overAllMood" , oldMoodItem.overAllMood)
            .whereEqualTo("q1Ans" , oldMoodItem.q1Ans)
            .whereEqualTo("q2Ans" , oldMoodItem.q2Ans)
            .whereEqualTo("q3Ans" , oldMoodItem.q3Ans)
            .whereEqualTo("q4Ans" , oldMoodItem.q4Ans)
            .whereEqualTo("q5Ans" , oldMoodItem.q5Ans)
            .whereEqualTo("year" , oldMoodItem.year)
            .get().await()


        if(recordQuery.documents.isNotEmpty()){

            return try {
                val result = Pair(true, "Mood updated...")
                for(doc in recordQuery.documents){
                    recordRef.document(doc.id).set(
                        updateMap,
                        SetOptions.merge()
                    ).await()


                    break
                }
                return result
            }
            catch (e:Exception){
                val result = Pair(true, e.message.toString())
                return result
            }
        }else{
            // Log.d("FIREBASEDATASOURCE","Record not found")
            val result = Pair(true, "Fatal Error")
            return result
        }

    }




    suspend fun getTodaysMood():Pair<Boolean,MoodItem?>{
        val UID = firebaseAuth.currentUser?.uid
        val year= calender.get( Calendar.YEAR)
        val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
        val month = monthFormat.format(calender.time)

        val refPath = "/mental_health_app/UserRecords/$UID/MoodRecords/$month$year"
        val recordRef = Firebase.firestore.collection(refPath)

        val recordQuery = recordRef.whereEqualTo("date", calender.get(Calendar.DAY_OF_MONTH) )
            .whereEqualTo("month",month).whereEqualTo("year", year)
            .limit(1).get().await()

        if(recordQuery.documents.isEmpty()){
            val resultPair =  Pair(false ,null)
            return resultPair
        }else{
            for(doc in recordQuery.documents){
                val moodItem = doc.toObject(MoodItem::class.java)
                val resultPair =  Pair(true,moodItem)
                return resultPair
            }
        }
        val resultPair =  Pair(false ,null)
        return resultPair
    }
}