package com.example.mentalhealthapp.dataclasses

import java.time.Month

data class MoodItem(
    val date:Int,
    val day:String,
    val month:String,
    val year :Int,
    val isEmpty:Boolean,
    val moodDescription: MoodDescription
)
