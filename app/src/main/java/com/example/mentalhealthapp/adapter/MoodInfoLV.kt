package com.example.mentalhealthapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.dataclasses.MoodItem
import com.google.android.material.textfield.TextInputEditText

class MoodInfoLV(val myContext: Context):BaseAdapter() {

    private var moodItem:MoodItem? = null
    override fun getCount(): Int {
        return 6
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        if(position == 0){
            val rootView = LayoutInflater.from(myContext).inflate(R.layout.mood_info_item,parent,false)
            moodItem?.let {
                val tvDay = rootView.findViewById<TextView>(R.id.tvDay)
                val tvDateMonth = rootView.findViewById<TextView>(R.id.tvDateMonth)
                val tvYear = rootView.findViewById<TextView>(R.id.tvYear)
                val tvMood1 = rootView.findViewById<TextView>(R.id.tvMood1)
                tvMood1.text = it.overAllMood
                tvDay.text= it.day
                tvYear.text = it.year.toString()
                tvDateMonth.text = "${it.month},${it.date}"
                val ivMood = rootView.findViewById<ImageView>(R.id.ivMood1)
                when(it.overAllMood){
                    "Happy"->{
                        ivMood.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.smile))
                    }
                    "Sad"->{
                        ivMood.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.sad))
                    }
                    "Anxious"->{
                        ivMood.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.sick))
                    }
                    "Angry"->{
                        ivMood.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.angry))
                    }
                    "Neutral"->{
                        ivMood.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.neutral))
                    }
                }
            }
            return rootView
        }
        else{
            val rootView = LayoutInflater.from(myContext).inflate(R.layout.mood_info_item_1,parent,false)
            moodItem?.let {
                val tvReason = rootView.findViewById<TextView>(R.id.tvReason)
                val tvAns = rootView.findViewById<TextView>(R.id.tvAns)

                when(position){
                    1->{
                        tvAns.text =  it.q1Ans
                        tvReason.text = "Reason for the mood"
                    }
                    2->{
                        if(it.q2Ans != ""){
                            tvAns.text =  it.q2Ans
                            tvReason.text = "Day overview"
                        }
                        else{
                            tvAns.text = it.q2Ans
                            tvReason.text = "Day overview"
                        }

                    }
                    3->{
                        tvAns.text =  it.q3Ans
                        tvReason.text = "Energy level throughout the day"
                    }
                    4->{
                        tvAns.text = it.q4Ans
                        tvReason.text = "Last night sleep status"
                    }
                    5->{
                        tvAns.text = it.q5Ans
                        tvReason.text = "Comments"
                    }
                }
            }
            return rootView
        }
    }
    fun updateMoodItem(newMoodItem: MoodItem){
        moodItem= newMoodItem
        notifyDataSetChanged()
    }


}