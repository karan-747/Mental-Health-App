package com.example.mentalhealthapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.databinding.FragmentSignUpBinding
import com.example.mentalhealthapp.viewmodels.LoginSignUpVM
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private lateinit var  binding :FragmentSignUpBinding
    private  lateinit var  viewModel :LoginSignUpVM
    private val REQUEST_CODE_SIGN_IN = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up,container,false)
        viewModel = ViewModelProvider(this)[LoginSignUpVM::class.java]

        binding.btnSignUp.setOnClickListener {
            if (checkEntries()) {
                signUpUser()
            }
        }


        binding.cvSignUpWithGoogle.setOnClickListener {
            signInUserWithGoogle()
        }

        return binding.root
    }

    private fun signInUserWithGoogle() {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(resources.getString(R.string.web_client_id))
            .requestEmail().build()

        val signInClient = GoogleSignIn.getClient( requireContext() ,options)
        signInClient.signInIntent.also {

            startActivityForResult(it,REQUEST_CODE_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_SIGN_IN){
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
            account?.let {
                googleAuthForFireBase(it)
            }
        }
    }
    private fun googleAuthForFireBase(account: GoogleSignInAccount) {


        CoroutineScope(Dispatchers.IO).launch {

            val result = viewModel.signInWithGoogle(account)
            if(result.first){
                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(),result.second,Toast.LENGTH_SHORT).show()
                    navigateToHome()
                }
            }else{
                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(),result.second,Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun signUpUser() {
        val email = binding.etSignUpEmail.text.toString()
        val password = binding.etSignUpPassword.text.toString()
       CoroutineScope(Dispatchers.IO).launch {
           val result = viewModel.signUpWithEmailAndPassword(email, password)
           if(result.first){
               withContext(Dispatchers.Main){
                   Toast.makeText(requireContext(),"Sign Up successful...",Toast.LENGTH_SHORT).show()
                   navigateToHome()
               }
           }else{
               withContext(Dispatchers.Main){
                   Toast.makeText(requireContext(),result.second,Toast.LENGTH_SHORT).show()
               }
           }

       }
    }

    private fun navigateToHome (){
        binding.root.findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
    }

    private fun checkEntries():Boolean{
        val email = binding.etSignUpEmail.text.toString()
        val password = binding.etSignUpPassword.text.toString()
        val passwordConfirmed = binding.etConfirmedPassword.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty() && passwordConfirmed.isNotEmpty()){
            if(password != passwordConfirmed){
                Toast.makeText(requireContext(),"Enter Credentials...",Toast.LENGTH_SHORT).show()
                return false
            }
            else{
                return true

            }
        }
        Toast.makeText(requireContext(),"Enter Credentials...",Toast.LENGTH_SHORT).show()
        return false

    }
}