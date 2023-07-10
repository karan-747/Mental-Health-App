package com.example.mentalhealthapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.databinding.FragmentMoodEntryBinding
import com.example.mentalhealthapp.dataclasses.MoodDescription
import com.example.mentalhealthapp.dataclasses.MoodItem
import com.example.mentalhealthapp.viewmodels.MoodEntryFragVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MoodEntryFragment : Fragment(R.layout.fragment_mood_entry) {
    private val TAG = "MoodEntryFragment"

    private lateinit var binding:FragmentMoodEntryBinding
    private lateinit var moodEntryFragVM: MoodEntryFragVM
    private lateinit var calender : Calendar
    private var q1Ans = ""
    private var q2Ans = ""
    private var q3Ans = ""
    private var q4Ans = ""
    private var q5Ans = ""
    private var mood = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_mood_entry,
        container,
        false)
        moodEntryFragVM = ViewModelProvider(this)[MoodEntryFragVM::class.java]
        calender = Calendar.getInstance()

        removeSelection()

        binding.cvHappy.setOnClickListener {
            removeSelection()
            binding.cvHappy.strokeWidth = 4
            binding.cvHappy.elevation = 5F
            mood="Happy"
        }

        binding.cvAnxious.setOnClickListener {
            removeSelection()
            binding.cvAnxious.strokeWidth = 4
            binding.cvAnxious.elevation = 5F
            mood="Anxious"
        }

        binding.cvAngry.setOnClickListener {
            removeSelection()
            binding.cvAngry.strokeWidth = 4
            binding.cvAngry.elevation = 5F
            mood="Angry"
        }

        binding.cvNeutral.setOnClickListener {
            removeSelection()
            binding.cvNeutral.strokeWidth = 4
            binding.cvNeutral.elevation = 5F
            mood="Neutral"
        }
        binding.cvSad.setOnClickListener {
            removeSelection()
            binding.cvSad.strokeWidth = 4
            binding.cvSad.elevation = 5F
            mood="Sad"
        }

        binding.btnSave.setOnClickListener(){
            if(mood != ""){
                makeMoodRecord()
            }
            else{
                Toast.makeText(requireContext(),"Please select your mood...",Toast.LENGTH_SHORT).show()
            }
        }





        return binding.root
    }

    private fun makeMoodRecord() {
        q1Ans = binding.etQ1.text.toString()
        q2Ans = binding.etQ2.text.toString()
        q3Ans = binding.etQ3.text.toString()
        q4Ans = binding.etQ4.text.toString()
        q5Ans = binding.etQ5.text.toString()
        val moodDescription= MoodDescription(q1Ans,q2Ans,q3Ans,q4Ans,q4Ans,mood)
        val date = calender.get( Calendar.DAY_OF_MONTH)
        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val day = dayFormat.format(calender.time)
        val year= calender.get( Calendar.YEAR)
        val monthFormat = SimpleDateFormat("MMMM",Locale.getDefault())
        val month = monthFormat.format(calender.time)
        Log.d(TAG,"$day, $date $month $year")
        Log.d(TAG,mood)
        Log.d(TAG,q1Ans)
        Log.d(TAG,q2Ans)
        Log.d(TAG,q3Ans)
        Log.d(TAG,q4Ans)
        Log.d(TAG,q5Ans)

        val moodItem = MoodItem(date, day, month, year, false, moodDescription)

        addtheUserMood(moodItem)



    }

    private fun addtheUserMood(moodItem: MoodItem) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = moodEntryFragVM.updateUserMood(moodItem)
            if(result.first){
                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(),result.second,Toast.LENGTH_SHORT).show()
                    binding.root.findNavController().navigate(R.id.action_moodEntryFragment_to_homeFragment)
                }

            }else{
                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(),result.second,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private  fun removeSelection(){
        binding.cvAngry.strokeWidth = 0
        binding.cvAnxious.strokeWidth = 0
        binding.cvSad.strokeWidth = 0
        binding.cvHappy.strokeWidth = 0
        binding.cvNeutral.strokeWidth = 0



        binding.cvAngry.elevation = 2F
        binding.cvAnxious.elevation = 2F
        binding.cvSad.elevation = 2F
        binding.cvHappy.elevation = 2F
        binding.cvNeutral.elevation = 2F



    }



}