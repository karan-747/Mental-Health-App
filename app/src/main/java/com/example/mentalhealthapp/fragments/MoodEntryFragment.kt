package com.example.mentalhealthapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
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
import java.util.HashMap
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
    private var oldMood = ""
    private var todayMoodStatus =false
    private lateinit var oldMoodItem:MoodItem

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
            if(!binding.progressBar.isVisible){
                removeSelection()
                binding.cvHappy.strokeWidth = 4
                binding.cvHappy.elevation = 5F
                mood="Happy"
            }

        }

        binding.cvAnxious.setOnClickListener {
            if(!binding.progressBar.isVisible){
                removeSelection()
                binding.cvAnxious.strokeWidth = 4
                binding.cvAnxious.elevation = 5F
                mood="Anxious"
            }

        }

        binding.cvAngry.setOnClickListener {
            if(!binding.progressBar.isVisible){
                removeSelection()
                binding.cvAngry.strokeWidth = 4
                binding.cvAngry.elevation = 5F
                mood="Angry"
            }

        }

        binding.cvNeutral.setOnClickListener {

            if(!binding.progressBar.isVisible){
                removeSelection()
                binding.cvNeutral.strokeWidth = 4
                binding.cvNeutral.elevation = 5F
                mood="Neutral"
            }

        }
        binding.cvSad.setOnClickListener {

            if(!binding.progressBar.isVisible){
                removeSelection()
                binding.cvSad.strokeWidth = 4
                binding.cvSad.elevation = 5F
                mood="Sad"
            }

        }

        binding.btnSave.setOnClickListener(){
            if(!binding.progressBar.isVisible){
                if(mood != ""){
                    if(todayMoodStatus){
                        makemoodMap()
                    }else{
                        makeMoodRecord()
                    }

                }
                else{
                    Toast.makeText(requireContext(),"Please select your mood...",Toast.LENGTH_SHORT).show()
                }
            }
        }


        getTodaysMoodOfUser()







        return binding.root
    }

    private fun makeOldRecord() {
        val date = calender.get( Calendar.DAY_OF_MONTH)
        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val day = dayFormat.format(calender.time)
        val year= calender.get( Calendar.YEAR)
        val monthFormat = SimpleDateFormat("MMMM",Locale.getDefault())
        val month = monthFormat.format(calender.time)

        Log.d(TAG,"$day, $date $month $year")
        Log.d(TAG,oldMood)
        Log.d(TAG,q1Ans)
        Log.d(TAG,q2Ans)
        Log.d(TAG,q3Ans)
        Log.d(TAG,q4Ans)
        Log.d(TAG,q5Ans)
        oldMoodItem = MoodItem(date, day,false, month,oldMood,q1Ans,q2Ans,q3Ans,q4Ans,q4Ans,year )

    }

    private fun makemoodMap() {
        q1Ans = binding.etQ1.text.toString()
        q2Ans = binding.etQ2.text.toString()
        q3Ans = binding.etQ3.text.toString()
        q4Ans = binding.etQ4.text.toString()
        q5Ans = binding.etQ5.text.toString()

        val date = calender.get( Calendar.DAY_OF_MONTH)
        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val day = dayFormat.format(calender.time)
        val year= calender.get( Calendar.YEAR)
        val monthFormat = SimpleDateFormat("MMMM",Locale.getDefault())
        val month = monthFormat.format(calender.time)
//        Log.d(TAG,"$day, $date $month $year")
//        Log.d(TAG,oldMood)
//        Log.d(TAG,q1Ans)
//        Log.d(TAG,q2Ans)
//        Log.d(TAG,q3Ans)
//        Log.d(TAG,q4Ans)
//        Log.d(TAG,q5Ans)
        val updateMap = hashMapOf<String,Any>( "date" to date,"day" to day,"empty" to false, "month" to month , "overAllMood" to mood,
                    "q1Ans" to q1Ans,"q2Ans" to q2Ans,"q3Ans" to q3Ans,"q4Ans" to q4Ans,"q5Ans" to q5Ans,
            "year" to year
                    )

        updateTheMoodRecord(updateMap)
    }

    private fun updateTheMoodRecord(updateMap: HashMap<String, Any>) {
        binding.progressBar.visibility = View.VISIBLE
        Log.d(TAG,oldMoodItem.date.toString())
        Log.d(TAG,oldMoodItem.day)
        Log.d(TAG,oldMoodItem.empty.toString())
        Log.d(TAG,oldMoodItem.month)
        Log.d(TAG,oldMoodItem.overAllMood)
        Log.d(TAG,oldMoodItem.q1Ans)
        Log.d(TAG,oldMoodItem.q2Ans)
        Log.d(TAG,oldMoodItem.q3Ans)
        Log.d(TAG,oldMoodItem.q4Ans)
        Log.d(TAG,oldMoodItem.q5Ans)
        CoroutineScope(Dispatchers.IO).launch {
            val result = moodEntryFragVM.updateUserMoodItem(updateMap,oldMoodItem)
            withContext(Dispatchers.Main){
                if(result.first){
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),result.second,Toast.LENGTH_SHORT).show()
                    binding.root.findNavController().navigate(R.id.action_moodEntryFragment_to_homeFragment)

                }else{

                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),result.second,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun getTodaysMoodOfUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = moodEntryFragVM.getTodayMoodEntry()
            withContext(Dispatchers.Main){
                if(result.first){
                    todayMoodStatus = true
                    binding.etQ1.setText(result.second?.q1Ans)
                    binding.etQ2.setText(result.second?.q2Ans)
                    binding.etQ3.setText(result.second?.q3Ans)
                    binding.etQ4.setText(result.second?.q4Ans)
                    binding.etQ5.setText(result.second?.q5Ans)
                    oldMood = result.second?.overAllMood!!
                    mood = result.second?.overAllMood!!

                    result.second?.let {
                        oldMoodItem =MoodItem(it.date,it.day,it.empty,it.month,it.overAllMood,it.q1Ans,it.q2Ans,it.q3Ans,it.q4Ans,it.q5Ans,it.year)
                    }

                    selectTheMoodCard(result.second?.overAllMood)
                    //makeOldRecord()
                    binding.progressBar.visibility = View.GONE
                }else{
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun selectTheMoodCard(overAllMood: String?) {
        when(overAllMood){
            "Happy"->{
                binding.cvHappy.strokeWidth= 4
                binding.cvHappy.elevation = 5F


            }
            "Sad"->{
                binding.cvSad.strokeWidth =4
                binding.cvSad.elevation = 5F
            }
            "Angry"->{
                binding.cvAngry.strokeWidth = 4
                binding.cvAngry.elevation = 5F
            }
            "Anxious"->{
                binding.cvAnxious.strokeWidth = 4
                binding.cvAnxious.elevation = 5F
            }
            "Neutral"->{
                binding.cvNeutral.strokeWidth = 4
                binding.cvNeutral.elevation = 5F
            }



        }
    }

    private fun makeMoodRecord() {
        q1Ans = binding.etQ1.text.toString()
        q2Ans = binding.etQ2.text.toString()
        q3Ans = binding.etQ3.text.toString()
        q4Ans = binding.etQ4.text.toString()
        q5Ans = binding.etQ5.text.toString()

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

        val moodItem = MoodItem(date, day,false, month,mood,q1Ans,q2Ans,q3Ans,q4Ans,q4Ans,year )

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