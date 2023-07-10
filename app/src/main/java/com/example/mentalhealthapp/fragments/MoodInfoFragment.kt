package com.example.mentalhealthapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.adapter.MoodInfoLV
import com.example.mentalhealthapp.databinding.FragmentMoodEntryBinding
import com.example.mentalhealthapp.databinding.FragmentMoodInfoBinding
import com.example.mentalhealthapp.dataclasses.MoodItem
import com.google.gson.Gson
import com.google.gson.JsonElement


class MoodInfoFragment : Fragment() {
   private  lateinit var binding: FragmentMoodInfoBinding
    private var myMoodItem = MoodItem()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_mood_info,container,false)
        val record = arguments?.getStringArray("RECORD")

        record?.get(1).let {
             myMoodItem = Gson().fromJson(it,MoodItem::class.java)
        }
        val myAdapter = MoodInfoLV(requireContext())
        binding.lvMood.apply {
            adapter = myAdapter
            myAdapter.updateMoodItem(myMoodItem)
        }

        return binding.root
    }
}