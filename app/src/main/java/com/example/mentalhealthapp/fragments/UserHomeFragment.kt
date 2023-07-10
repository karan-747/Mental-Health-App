package com.example.mentalhealthapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.databinding.FragmentUserHomeBinding


class UserHomeFragment : Fragment(R.layout.fragment_user_home) {

    private lateinit var binding: FragmentUserHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=  DataBindingUtil.inflate(inflater, R.layout.fragment_user_home,container,false)

        binding.floatingActionButton.setOnClickListener(){
            it.findNavController().navigate(R.id.action_homeFragment_to_moodEntryFragment)
        }

        return binding.root

    }
}