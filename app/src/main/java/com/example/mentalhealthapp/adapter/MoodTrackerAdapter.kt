package com.example.mentalhealthapp.adapter

import android.content.Context
import android.icu.text.Transliterator.Position
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

class MoodTrackerAdapter(private val myContext: Context) :BaseAdapter() {
    override fun getCount(): Int {
        return 31
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {

        val rootView = LayoutInflater.from(myContext).inflate(R.layout.grid_item,parent,false)
        val ivMood = rootView.findViewById<ImageView>(R.id.ivMood)
        val tvDate = rootView.findViewById<TextView>(R.id.tvDate)
        tvDate.text = (position+1).toString()
        ivMood.setImageDrawable(ContextCompat.getDrawable(myContext,R.drawable.ic_happy))

        return rootView

    }
}