package com.example.mentalhealthapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.adapter.MoodTrackerAdapter
import com.example.mentalhealthapp.databinding.FragmentMoodTrackBinding


class MoodTrackFragment : Fragment(R.layout.fragment_mood_track) {

    private lateinit var  binding: FragmentMoodTrackBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_mood_track,container,false)



        val myAdapter= MoodTrackerAdapter(requireContext())

        binding.gridView.adapter = myAdapter

        return  binding.root


    }
}