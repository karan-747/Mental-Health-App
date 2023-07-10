package com.example.mentalhealthapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.databinding.FragmentUserHomeBinding
import com.google.firebase.auth.FirebaseAuth


class UserHomeFragment : Fragment(R.layout.fragment_user_home) {

    private lateinit var binding: FragmentUserHomeBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=  DataBindingUtil.inflate(inflater, R.layout.fragment_user_home,container,false)

        binding.floatingActionButton.setOnClickListener(){
            it.findNavController().navigate(R.id.action_homeFragment_to_moodEntryFragment)
        }

        binding.ivEdit.setOnClickListener(){
            binding.root.findNavController().navigate(R.id.action_homeFragment_to_editProfileFragment)
        }
        binding.ivLogout.setOnClickListener {
            firebaseAuth.signOut()
            binding.root.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        return binding.root

    }

    override fun onResume() {
        super.onResume()
        firebaseAuth.currentUser?.let { user->

            if(user.displayName != null){
                binding.tvUserName.text =user.displayName
            }
            if(user.photoUrl != null){

                Glide.with(this).load(user.photoUrl)
                    .error(R.drawable.blankmood)
                    .into(binding.ivUserImg)

            }
        }
    }
}