package com.example.diet_app.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diet_app.databinding.FragmentLogFoodBinding
import com.example.diet_app.menu.ClassFood
import com.example.diet_app.menu.FoodAdapter
import com.example.diet_app.menu.FoodViewModel

class LogFoodFragment : Fragment() {

    private lateinit var viewModel: FoodViewModel
    private lateinit var binding: FragmentLogFoodBinding
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var logFoodAdapter: FoodAdapter
    private val loggedFoods = mutableListOf<ClassFood>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogFoodBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        foodAdapter = FoodAdapter { food ->
            logFood(food)
        }
        binding.rvFood.adapter = foodAdapter
        binding.rvFood.layoutManager = LinearLayoutManager(context)

        // Observe foodList LiveData from the ViewModel
        viewModel.foodList.observe(viewLifecycleOwner, Observer { foods ->
            foods?.let {
                foodAdapter.setFoodList(it)
            }
        })

        logFoodAdapter = FoodAdapter { /* No action needed for logging food */ }
        logFoodAdapter.setFoodList(loggedFoods)
        binding.rvListFood.adapter = logFoodAdapter
        binding.rvListFood.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    private fun logFood(food: ClassFood) {
        loggedFoods.add(food)
        logFoodAdapter.setFoodList(loggedFoods)
    }
}
