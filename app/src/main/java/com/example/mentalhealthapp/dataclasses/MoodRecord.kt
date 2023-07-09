package com.example.mentalhealthapp.dataclasses

data class MoodRecord(

    val monthYear:String,
    val month:Int,
    val year:Int,
    val moodRecord:ArrayList<MoodItem>
)
