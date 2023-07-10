package com.example.mentalhealthapp.fragments

import android.icu.text.DateFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.adapter.MoodTrackerAdapter
import com.example.mentalhealthapp.databinding.FragmentMoodTrackBinding
import com.example.mentalhealthapp.viewmodels.MoodTrackVM
import com.google.gson.Gson
import com.google.type.DateTime
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MoodTrackFragment(val reset:()->Unit) : Fragment(R.layout.fragment_mood_track) {

    private val TAG = "MoodTrackFragment"
    private lateinit var  binding: FragmentMoodTrackBinding
    private lateinit var viewModel :MoodTrackVM
    private val calendar = Calendar.getInstance()
    private  lateinit var  myAdapter:MoodTrackerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_mood_track,container,false)
        viewModel = ViewModelProvider(this)[MoodTrackVM::class.java]
        viewModel.initDatabase()
         myAdapter= MoodTrackerAdapter(requireContext()){

             val bundle =  Bundle()
             bundle.putStringArray("RECORD" , arrayOf( "yess",Gson().toJson(it)))
             reset.invoke()
             binding.root.findNavController().navigate(R.id.action_homeFragment_to_moodInfoFragment,bundle)
         }
        binding.gridView.adapter = myAdapter

        val year= calendar.get( Calendar.YEAR)
        val monthFormat = SimpleDateFormat("MMMM",Locale.getDefault())
        val month = monthFormat.format(calendar.time)
        binding.tvDate.text = "$month, $year"


        return  binding.root
    }

    private fun getStartPosition():Int{
        val mycal =Calendar.getInstance()
        mycal.set(Calendar.HOUR_OF_DAY,0)
        mycal.clear(Calendar.MINUTE)
        mycal.clear(Calendar.SECOND)
        mycal.clear(Calendar.MILLISECOND)
        mycal.set(Calendar.DAY_OF_MONTH, 1);
        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val day = dayFormat.format(mycal.time)

        return when(day){

            "Sunday"->{
                0
            }
            "Monday"->{
                1
            }
            "Tuesday"->{
                2
            }
            "Wednesday"->{
                3

            }
            "Thursday"->{
                4

            }
            "Friday"->{
                5

            }
            "Saturday"->{
                6

            }


            else -> { 0}
        }
    }

    private fun getEndPosition(): Int {
        val days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        //Toast.makeText(requireContext(),days.toString(),Toast.LENGTH_SHORT).show()
        return getStartPosition() +days
    }


    override fun onResume() {
        super.onResume()
        viewModel.getMoodRecordLiveData().observe(viewLifecycleOwner, Observer {
            myAdapter.updateList(it,getStartPosition(),getEndPosition())
        })

    }
}