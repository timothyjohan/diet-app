package com.example.diet_app.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_app.databinding.FragmentPostRecyclerviewBinding

class FoodAdapter(private val clickListener: (ClassFood) -> Unit) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private var foodList: List<ClassFood> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = FragmentPostRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position], clickListener)
    }

    override fun getItemCount(): Int = foodList.size

    fun setFoodList(foods: List<ClassFood>) {
        foodList = foods
        notifyDataSetChanged()
    }

    inner class FoodViewHolder(private val binding: FragmentPostRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(food: ClassFood, clickListener: (ClassFood) -> Unit) {
            binding.tvFood.text = food.foodName
            binding.tvCalorie.text = "Calories: ${food.calories}"
            binding.tvFat.text = "Fat: ${food.fats}"
            binding.tvProtein.text = "Protein: ${food.protein}"
            binding.tvCarbohydrate.text = "Carbs: ${food.carbs}"
            binding.ibAdd.setOnClickListener {
                clickListener(food)
            }
        }
    }
}
