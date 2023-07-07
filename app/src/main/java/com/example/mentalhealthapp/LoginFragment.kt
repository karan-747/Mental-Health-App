package com.example.mentalhealthapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mentalhealthapp.databinding.FragmentLoginBinding
import com.example.mentalhealthapp.viewmodels.LoginSignUpVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var viewModel: LoginSignUpVM
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        viewModel = ViewModelProvider(this)[LoginSignUpVM::class.java]

        binding.btnLogin.setOnClickListener {

            if(checkEntries()){
                loginUser()
                //navigateToHome()
            }

        }

        binding.tvSigunUp.setOnClickListener(){
            navigateToSignUpFragment()
        }

        binding.cvLoginWithGoogle.setOnClickListener {
            viewModel.logoutUser()
        }


        return binding.root



    }

    private fun loginUser(){
        val email = binding.etLoginEmail.text.toString()
        val password = binding.etLoginPassword.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            val result = viewModel.logInWithEmailAndPassword(email, password)
            if(result.first){
                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(),"Logged In...",Toast.LENGTH_SHORT).show()
                    navigateToHome()
                }
            }else{
                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(),result.second,Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    private fun navigateToSignUpFragment() {
        binding.root.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
    }

    private fun navigateToHome() {
        binding.root.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun checkEntries():Boolean{
        val email = binding.etLoginEmail.text.toString()
        val password = binding.etLoginPassword.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()){
            return true
        }
        Toast.makeText(requireContext(),"Enter credentials...",Toast.LENGTH_SHORT).show()
        return false
    }
}