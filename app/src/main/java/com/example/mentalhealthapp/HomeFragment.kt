package com.example.mentalhealthapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.mentalhealthapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)







        initTheFragment()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.title.toString()){

                "Home"->{
                    childFragmentManager.beginTransaction().apply {
                        replace(R.id.flBottomNav,UserHomeFragment())
                        commit()
                    }

                }
                "Guide"->{
                    childFragmentManager.beginTransaction().apply {
                        replace(R.id.flBottomNav,GuideFragment())
                        commit()

                    }

                }
                "Mood Tracker"->{
                    childFragmentManager.beginTransaction().apply {
                        replace(R.id.flBottomNav,MoodTrackFragment())
                        commit()

                    }
                }
                "Diary"->{
                    childFragmentManager.beginTransaction().apply {
                        replace(R.id.flBottomNav,GratitudeFragment())
                        commit()
                    }
                }
                else->{
                    childFragmentManager.beginTransaction().apply {
                        replace(R.id.flBottomNav,UserHomeFragment())
                        commit()
                    }
                }
            }

            true
        }


        return binding.root
    }

    private fun initTheFragment() {
        childFragmentManager.beginTransaction().apply {
            replace(R.id.flBottomNav,UserHomeFragment())
            commit()
        }
    }

}