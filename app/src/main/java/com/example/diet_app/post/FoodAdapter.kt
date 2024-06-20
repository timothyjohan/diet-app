package com.example.diet_app.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_app.R

class FoodAdapter(
    private val listFood: List<ClassFood>
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFood: TextView = itemView.findViewById(R.id.tvFood)
        val tvCalorie: TextView = itemView.findViewById(R.id.tvCalorie)
        val tvProtein: TextView = itemView.findViewById(R.id.tvProtein)
        val tvFat: TextView = itemView.findViewById(R.id.tvFat)
        val tvCarbohydrate: TextView = itemView.findViewById(R.id.tvCarbohydrate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.fragment_post_recyclerview, parent, false)
        return FoodViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return listFood.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val data = listFood[position]
        holder.tvFood.text = data.foodName
        holder.tvCalorie.text = "Calories: ${data.calories}"
        holder.tvProtein.text = "Protein: ${data.protein}"
        holder.tvFat.text = "Fat: ${data.fats}"
        holder.tvCarbohydrate.text = "Carbohydrate: ${data.carbs}"
    }
}
