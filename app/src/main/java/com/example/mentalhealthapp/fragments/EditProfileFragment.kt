package com.example.mentalhealthapp.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.databinding.FragmentEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {


    private lateinit var binding: FragmentEditProfileBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    private var userImgUri = Uri.parse("android.resource://com.example.trackexp/${R.drawable.blankmood}")
    private var defaultUserImgUri = Uri.parse("android.resource://com.example.trackexp/${R.drawable.blankmood}")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_profile,container,false)
        binding.progressBar2.isVisible =false
        firebaseAuth.currentUser?.let { user->

            if(user.displayName!=null){
                binding.etUserName.setText(user.displayName.toString())
                binding.tvGreetUser.text ="Hello, ${user.displayName}"
            }

            if(user.photoUrl != null){
                userImgUri = user.photoUrl
                Glide.with(this).load(user.photoUrl)
                    .error(R.drawable.blankmood)
                    .into(binding.ivUserImage)

            }
        }

        val pickMedia =registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
            if(it==null){
                Toast.makeText(requireContext(),"Could not load image...",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(),"Image loaded successfully...",Toast.LENGTH_SHORT).show()
                userImgUri =it
                Log.d("PHOTOPICKERPHOTOURI",it.toString())
                binding.ivUserImage.setImageURI(it)
            }
        }
        binding.ivUserImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.btnUpdateUserProfile.setOnClickListener {
            binding.progressBar2.isVisible = true
            updateUserProfile()

        }

        return binding.root
    }

    fun updateUserProfile(){
        val userName =  binding.etUserName.text.toString()
        val profileUpdate = UserProfileChangeRequest.Builder()
            .setDisplayName(userName)
            .setPhotoUri(userImgUri)
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                firebaseAuth.currentUser?.updateProfile(profileUpdate)?.await()
                withContext(Dispatchers.Main){
                    binding.progressBar2.isVisible = false
                    binding.tvGreetUser.text = "Hello, $userName"
                    Toast.makeText(requireContext(),"Profile updated...",Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    binding.progressBar2.isVisible = false
                    binding.tvGreetUser.text = "Hello, $userName"
                    Toast.makeText(requireContext(),"Profile updated...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}