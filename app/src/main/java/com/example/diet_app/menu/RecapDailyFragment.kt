package com.example.diet_app.menu

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.example.diet_app.data.CurrentUser
import com.example.diet_app.data.User
import com.example.diet_app.data.source.local.AppDatabase
import com.example.diet_app.databinding.FragmentCalculatorBinding
import com.example.diet_app.databinding.FragmentLogFoodBinding
import com.example.diet_app.databinding.FragmentRecapDailyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class RecapDailyFragment : Fragment() {
    private lateinit var binding: FragmentRecapDailyBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private var users: ArrayList<User> = ArrayList()
    private lateinit var db: AppDatabase

    private lateinit var daysOfWeekAdapter: DaysOfWeekAdapter
    private lateinit var datesAdapter: DatesAdapter
    private val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    @RequiresApi(Build.VERSION_CODES.O)
    private var currentMonth: YearMonth = YearMonth.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecapDailyBinding.inflate(inflater, container, false)
        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "DBCalorieCraft").fallbackToDestructiveMigration().build()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupMonthHeader()

        binding.btnPrevMonth.setOnClickListener {
            currentMonth = currentMonth.minusMonths(1)
            updateCalendar()
        }

        binding.btnNextMonth.setOnClickListener {
            currentMonth = currentMonth.plusMonths(1)
            updateCalendar()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupMonthHeader() {
        binding.monthTextView.text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupRecyclerView() {
        daysOfWeekAdapter = DaysOfWeekAdapter(daysOfWeek)
        binding.daysOfWeekRecyclerView.layoutManager = GridLayoutManager(requireContext(), 7)
        binding.daysOfWeekRecyclerView.adapter = daysOfWeekAdapter

        updateCalendar()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateCalendar() {
        val days = generateCalendarDays(currentMonth)
        datesAdapter = DatesAdapter(days) { day ->
            Toast.makeText(requireContext(), "test", Toast.LENGTH_SHORT).show()
        }
        binding.datesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 7)
        binding.datesRecyclerView.adapter = datesAdapter
        setupMonthHeader()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateCalendarDays(month: YearMonth): List<Day> {
        val days = mutableListOf<Day>()
        val firstDayOfMonth = month.atDay(1)
        val daysInMonth = month.lengthOfMonth()
        val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7

        for (i in 0 until firstDayOfWeek) {
            days.add(Day(LocalDate.MIN, Color.TRANSPARENT))
        }

        for (i in 1..daysInMonth) {
            val date = month.atDay(i)
            val color = if (date == LocalDate.now()) { //aku gatau cara IF nya dari db utk ngambil hari2 yg sukses gimana
                Color.GREEN                            // tinggal diubah aja condition IF nya
            } else {                                   // sama tambahin 1 else lagi buat warna merah, ak jg ga paham conditionnya
                Color.WHITE
            }
            days.add(Day(date, color))
        }

        while (days.size % 7 != 0) {
            days.add(Day(LocalDate.MIN, Color.TRANSPARENT))
        }

        return days
    }
}