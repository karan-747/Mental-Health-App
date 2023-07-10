package com.example.mentalhealthapp.adapter

import android.content.Context
import android.icu.text.Transliterator.Position
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.dataclasses.MoodItem

class MoodTrackerAdapter(private val myContext: Context,val openMoodItem: (MoodItem)->Unit) :BaseAdapter() {
    private val TAG ="MoodTrackerAdapter"

    private val myRecordsList = ArrayList<MoodItem>()
    private var startPosition = -1
    private var endPosition = -1
    private var count  = -1
    override fun getCount(): Int {
        return 37
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {

        val rootView = LayoutInflater.from(myContext).inflate(R.layout.grid_item,parent,false)

        if(position<startPosition || position >endPosition ){
            rootView.visibility = View.GONE
            return rootView
        }
        else{
            for(moodItem in myRecordsList){
                //Log.d(TAG,"MoodItem ${moodItem.date}")
                if(count == moodItem.date ){
                    val ivMood = rootView.findViewById<ImageView>(R.id.ivMood)
                    val tvDate = rootView.findViewById<TextView>(R.id.tvDate)
                    tvDate.text = count.toString()
                    count++
                    when(moodItem.overAllMood){
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
                    rootView.setOnClickListener {
                        openMoodItem(moodItem)
                    }
                    Log.d(TAG,"FOUND $count")
                    return rootView
                }
            }

            val ivMood = rootView.findViewById<ImageView>(R.id.ivMood)
            val tvDate = rootView.findViewById<TextView>(R.id.tvDate)
            tvDate.text = count.toString()
            count++
            ivMood.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.blankmood))
            return rootView
        }


        return rootView
    }

    fun updateList(moodItems:ArrayList<MoodItem>,start:Int,end:Int){
        myRecordsList.clear()
        startPosition= start
        myRecordsList.addAll(moodItems)
        endPosition = end
        count =1
        notifyDataSetChanged()
    }
}