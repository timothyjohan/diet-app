package com.example.diet_app.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_app.R

class DaysOfWeekAdapter(private val daysOfWeek: List<String>) : RecyclerView.Adapter<DaysOfWeekAdapter.DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_day_of_week_item, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(daysOfWeek[position])
    }

    override fun getItemCount(): Int = daysOfWeek.size

    class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayTextView: TextView = itemView as TextView

        fun bind(day: String) {
            dayTextView.text = day
        }
    }
}