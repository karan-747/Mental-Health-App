package com.example.mentalhealthapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mentalhealthapp.dataclasses.MoodItem
import com.example.mentalhealthapp.repository.FirebaseRepository

class MoodEntryFragVM: ViewModel(){

    private val repoRef= FirebaseRepository
    suspend fun updateUserMood(userMoodItem: MoodItem):Pair<Boolean,String>{
        return repoRef.addUserMoodItem(userMoodItem)
    }
}