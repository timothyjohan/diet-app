package com.example.diet_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.diet_app.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding
    private lateinit var email: String
    private lateinit var config: Config

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val navArgs: SettingsFragmentArgs by navArgs<SettingsFragmentArgs>()
        email = navArgs.email

        config = Config(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swDaily.isChecked = config.dailyReminder
        binding.swWeekly.isChecked = config.weeklyReminder

        binding.btSaveSettings.setOnClickListener {
            config.dailyReminder = binding.swDaily.isChecked
            config.weeklyReminder = binding.swWeekly.isChecked
            Toast.makeText(requireContext(), "Settings updated", Toast.LENGTH_SHORT).show()
        }

        binding.btToDashboard.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToDashboardFragment(email)
            findNavController().navigate(action)
        }

        binding.btDelete.setOnClickListener() {
            val action = SettingsFragmentDirections.actionSettingsFragmentToLoginFragment2()
            findNavController().navigate(action)
        }
    }

}