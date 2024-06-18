package com.example.diet_app.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.diet_app.data.User
import com.example.diet_app.data.source.local.AppDatabase
import com.example.diet_app.databinding.FragmentCalculatorBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalculatorFragment : Fragment() {
    private lateinit var binding: FragmentCalculatorBinding
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private lateinit var db: AppDatabase
    private lateinit var userEmail: String
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "DBCalorieCraft"
        ).fallbackToDestructiveMigration().build()

        userEmail = CalculatorFragmentArgs.fromBundle(requireArguments()).email

        setupActivitySpinner()
        loadUserData()

        binding.btnCalculate.setOnClickListener {
            calculateTDEE()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun setupActivitySpinner() {
        val activityLevels = arrayOf(
            "Sedentary (office job)",
            "Light Exercise (1-2 days/week)",
            "Moderate Exercise (3-5 days/week)",
            "Heavy Exercise (6-7 days/week)",
            "Athlete (2x per day)"
        )
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, activityLevels)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
    }

    private fun loadUserData() {
        coroutineScope.launch {
            user = db.userDao().getUserByEmail(userEmail)
            if (user == null) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun calculateTDEE() {
        val weight = binding.etWeight.text.toString().toDoubleOrNull()
        val height = binding.etHeight.text.toString().toDoubleOrNull()
        val activityLevel = binding.spinner.selectedItem.toString()

        if (weight == null || height == null) {
            Toast.makeText(requireContext(), "Please enter valid weight and height", Toast.LENGTH_SHORT).show()
            return
        }

        if (user == null) {
            Toast.makeText(requireContext(), "User data not loaded", Toast.LENGTH_SHORT).show()
            return
        }

        val gender = user!!.gender
        val bmr = if (gender) {
            88.362 + (13.397 * weight) + (4.799 * height)
        } else {
            447.593 + (9.247 * weight) + (3.098 * height)
        }

        val tdee = when (activityLevel) {
            "Sedentary (office job)" -> bmr * 1.2
            "Light Exercise (1-2 days/week)" -> bmr * 1.375
            "Moderate Exercise (3-5 days/week)" -> bmr * 1.55
            "Heavy Exercise (6-7 days/week)" -> bmr * 1.725
            "Athlete (2x per day)" -> bmr * 1.9
            else -> bmr
        }

        requireActivity().runOnUiThread {
            binding.tvResult.visibility = View.VISIBLE
            binding.tvResultPrint.visibility = View.VISIBLE
            binding.tvResultPrint.text = "Your TDEE is %.2f calories per day.".format(tdee)
        }
    }
}
