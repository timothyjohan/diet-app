package com.example.diet_app.menu

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diet_app.SosmedApplication.Companion.postRepository
import com.example.diet_app.databinding.FragmentLogFoodBinding
//import com.example.diet_app.menu.String
import com.example.diet_app.menu.FoodAdapter
import com.example.diet_app.menu.FoodViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogFoodFragment : Fragment() {

    private lateinit var viewModel: FoodViewModel
    private lateinit var binding: FragmentLogFoodBinding
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var logFoodAdapter: FoodAdapter
    private val loggedFoods = mutableListOf<String>()

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

        binding.titleEtPost.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (it.isNotEmpty()) {
                        fetchAutocomplete(it.toString())
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No action needed
            }
        })
        return binding.root
    }

    private fun logFood(food: String) {
        loggedFoods.add(food)
        logFoodAdapter.setFoodList(loggedFoods)
    }
    private fun fetchAutocomplete(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: List<String> = postRepository.getAllPosts(query = query)
                withContext(Dispatchers.Main) {
                    foodAdapter.setFoodList(response)
                    Log.d("Reeee", response.toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
