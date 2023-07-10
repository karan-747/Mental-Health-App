package com.example.mentalhealthapp.dataclasses

import java.time.Month

data class MoodItem(
    val date:String,
    val day:String,
    val month:String,
    val year :String,
    val isEmpty:Boolean,
    val moodDescription: MoodDescription
)
