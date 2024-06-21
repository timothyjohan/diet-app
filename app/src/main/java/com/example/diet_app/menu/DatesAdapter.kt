package com.example.diet_app.menu

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_app.R
import java.time.LocalDate

class DatesAdapter(
    private val days: List<Day>,
    private val onDayClick: (Day) -> Unit
) : RecyclerView.Adapter<DatesAdapter.DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_date_item, parent, false)
        return DayViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = days[position]
        holder.bind(day, onDayClick)
    }

    override fun getItemCount(): Int = days.size

    class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayNumber: TextView = itemView.findViewById(R.id.day_number)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(day: Day, onDayClick: (Day) -> Unit) {
            if (day.color == Color.TRANSPARENT) {
                dayNumber.text = ""
            } else {
                dayNumber.text = day.date.dayOfMonth.toString()
                dayNumber.setBackgroundColor(day.color)
                itemView.setOnClickListener { onDayClick(day) }
            }
        }
    }
}

data class Day(
    val date: LocalDate,
    val color: Int
)