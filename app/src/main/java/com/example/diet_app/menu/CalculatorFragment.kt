package com.example.diet_app.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
    private lateinit var db: AppDatabase
    private lateinit var userEmail: String
    private var user: User? = null

    private val viewModel: CalculatorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "DBCalorieCraft"
        ).fallbackToDestructiveMigration().build()

        // Get the arguments passed from DashboardFragment
        userEmail = CalculatorFragmentArgs.fromBundle(requireArguments()).email

        setupActivitySpinner()
        loadUserData()

        binding.btnCalculate.setOnClickListener {
            calculateTDEE()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()  // Navigate back to the previous fragment
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
        CoroutineScope(Dispatchers.IO).launch {
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

        viewModel.calculateTDEE(weight, height, user!!.gender, activityLevel)
    }
}

@BindingAdapter("tdeeResultText")
fun setTdeeResultText(textView: TextView, tdeeResult: Double?) {
    tdeeResult?.let {
        textView.text = "${it.toInt()} Kalori"
    }
}
