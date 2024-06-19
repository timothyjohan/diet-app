package com.example.diet_app.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DaysOfWeekAdapter(
    private val daysOfWeek: List<String>
) : RecyclerView.Adapter<DaysOfWeekAdapter.DayOfWeekViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayOfWeekViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return DayOfWeekViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayOfWeekViewHolder, position: Int) {
        holder.bind(daysOfWeek[position])
    }

    override fun getItemCount(): Int = daysOfWeek.size

    class DayOfWeekViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(dayOfWeek: String) {
            textView.text = dayOfWeek
        }
    }
}
