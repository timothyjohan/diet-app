package com.example.diet_app.menu

import android.R
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import androidx.navigation.fragment.navArgs
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.example.diet_app.SosmedApplication
import com.example.diet_app.data.CalendarRequest
import com.example.diet_app.data.CalendarResponse
import com.example.diet_app.data.User
import com.example.diet_app.data.source.local.AppDatabase
import com.example.diet_app.databinding.FragmentRecapDailyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class RecapDailyFragment : Fragment() {
    private lateinit var binding: FragmentRecapDailyBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private var users: ArrayList<User> = ArrayList()
    private lateinit var db: AppDatabase
    private val postRepository = SosmedApplication.postRepository
    val navArgs: RecapDailyFragmentArgs by navArgs()
    @RequiresApi(Build.VERSION_CODES.O)
    var month = LocalDate.now().monthValue.toString()
    var before = 1
    @RequiresApi(Build.VERSION_CODES.O)
    var after = LocalDate.MAX

    private lateinit var daysOfWeekAdapter: DaysOfWeekAdapter
    private lateinit var datesAdapter: DatesAdapter
    private val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =FragmentRecapDailyBinding.inflate(inflater, container, false)
        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "DBCalorieCraft").fallbackToDestructiveMigration().build()


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupMonthHeader()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupMonthHeader() {
        val currentMonth = LocalDate.now().month
        binding.monthTextView.text = currentMonth.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupRecyclerView() {
        daysOfWeekAdapter = DaysOfWeekAdapter(daysOfWeek)
        binding.daysOfWeekRecyclerView.layoutManager = GridLayoutManager(requireContext(), 7)
        binding.daysOfWeekRecyclerView.adapter = daysOfWeekAdapter

        val days = generateCalendarDays(CalendarRequest(navArgs.email,before,after))
        datesAdapter = DatesAdapter(days) { day ->
            // kosongin aja.
        }
        binding.datesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 7)
        binding.datesRecyclerView.adapter = datesAdapter
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateCalendarDays(query: CalendarRequest): List<Day> {
        val days = mutableListOf<Day>()
        val firstDayOfMonth = LocalDate.now().withDayOfMonth(1)
        val daysInMonth = firstDayOfMonth.lengthOfMonth()

        val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
        for (i in 0 until firstDayOfWeek) {
            days.add(Day(LocalDate.now(), Color.TRANSPARENT))
        }

        coroutine.launch {
            val calendarRequest = CalendarRequest(navArgs.email,before,after)
            calendarColor(calendarRequest, days)
            for (i in 1..daysInMonth) {
                val date = firstDayOfMonth.withDayOfMonth(i)
                val color = if (date == LocalDate.now()) { //aku gatau cara IF nya dari db utk ngambil hari2 yg sukses gimana
                    Color.GREEN                            // tinggal diubah aja condition IF nya
                } else {                                   // sama tambahin 1 else lagi buat warna merah, ak jg ga paham conditionnya
                    Color.WHITE
                }
                days.add(Day(date, color))
            }
        }
        return days
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calendarColor(query: CalendarRequest, days: MutableList<Day>): Response<CalendarResponse>{
        lateinit var response: Response<CalendarResponse>
        val firstDayOfMonth = LocalDate.now().withDayOfMonth(1)
        val daysInMonth = firstDayOfMonth.lengthOfMonth()
        coroutine.launch {
            response = postRepository.getDates(query)
            for (i in 1..daysInMonth) {
                val date = firstDayOfMonth.withDayOfMonth(i)
                val color = if (date == LocalDate.now()) { //aku gatau cara IF nya dari db utk ngambil hari2 yg sukses gimana
                    Color.GREEN                            // tinggal diubah aja condition IF nya
                } else {                                   // sama tambahin 1 else lagi buat warna merah, ak jg ga paham conditionnya
                    Color.WHITE
                }
                days.add(Day(date, color))
            }
        }
        return response
    }

}
