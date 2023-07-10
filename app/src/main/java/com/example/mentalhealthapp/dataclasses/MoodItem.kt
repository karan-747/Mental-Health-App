package com.example.mentalhealthapp.dataclasses

data class MoodItem(
    val date:Int=-1,
    val day:String ="",
    val empty:Boolean=false,
    val month:String = "",
    val overAllMood:String = "",

    val q1Ans:String="",
    val q2Ans:String="",
    val q3Ans:String="",
    val q4Ans:String="",
    val q5Ans:String="",

    val year :Int =-1

    //val moodDescription: MoodDescription
)
