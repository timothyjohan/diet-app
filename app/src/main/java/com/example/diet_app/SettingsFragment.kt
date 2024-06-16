package com.example.diet_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diet_app.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding
    var dailyReminder=false
    var weeklyReminder=false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(dailyReminder == false){
            binding.rbDailyOn.isChecked=true
            binding.rbDailyOff.isChecked=false
        }
        else if(dailyReminder == true){
            binding.rbDailyOff.isChecked=true
            binding.rbDailyOn.isChecked=false
        }

        if(weeklyReminder == false){
            binding.rbWeeklyOn.isChecked=true
            binding.rbWeeklyOff.isChecked=false
        }
        else if(weeklyReminder == true){
            binding.rbWeeklyOff.isChecked=true
            binding.rbWeeklyOn.isChecked=false
        }

        binding.btSaveSettings.setOnClickListener(){
            if(binding.rbDailyOn.isChecked){
                dailyReminder=true
            }
            else if(binding.rbDailyOff.isChecked){
                dailyReminder=false
            }

            if(binding.rbWeeklyOn.isChecked){
                weeklyReminder=true
            }
            else if(binding.rbWeeklyOff.isChecked){
                weeklyReminder=false
            }
        }
    }

}