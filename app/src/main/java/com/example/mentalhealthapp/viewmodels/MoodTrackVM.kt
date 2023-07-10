package com.example.mentalhealthapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mentalhealthapp.dataclasses.MoodItem
import com.example.mentalhealthapp.repository.FirebaseRepository

class MoodTrackVM: ViewModel() {

    private val repoRef = FirebaseRepository

    fun initDatabase(){
        repoRef.getTheDatabase()
    }
    fun getMoodRecordLiveData(): LiveData<ArrayList<MoodItem>> {
        return repoRef.getRecordLiveData()
    }

}