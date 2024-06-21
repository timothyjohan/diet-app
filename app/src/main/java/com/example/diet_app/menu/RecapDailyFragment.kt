package com.example.diet_app.menu

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.example.diet_app.R
import com.example.diet_app.SosmedApplication
import com.example.diet_app.data.CalendarRequest
import com.example.diet_app.data.CalendarResponse
import com.example.diet_app.data.User
import com.example.diet_app.data.source.local.AppDatabase
import com.example.diet_app.databinding.FragmentRecapDailyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class RecapDailyFragment : Fragment() {
    private lateinit var binding: FragmentRecapDailyBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private var users: ArrayList<User> = ArrayList()
    private lateinit var db: AppDatabase
    private val postRepository = SosmedApplication.postRepository
    val arrayTemp:ArrayList<String> = ArrayList()
    val navArgs: RecapDailyFragmentArgs by navArgs()
    @RequiresApi(Build.VERSION_CODES.O)
    var month = LocalDate.now().monthValue.toString()
    @RequiresApi(Build.VERSION_CODES.O)
    var before = "2024-06-30"
    @RequiresApi(Build.VERSION_CODES.O)
    var after = "2024-06-01"

    private lateinit var daysOfWeekAdapter: DaysOfWeekAdapter
    private lateinit var datesAdapter: DatesAdapter
    private val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    @RequiresApi(Build.VERSION_CODES.O)
    var currentMonth = LocalDate.now().withDayOfMonth(1)


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

        binding.monthTextView.text = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        binding.root.findViewById<Button>(R.id.prevMonthButton).setOnClickListener { changeMonth(-1) }
        binding.root.findViewById<Button>(R.id.nextMonthButton).setOnClickListener { changeMonth(1) }

        daysOfWeekAdapter = DaysOfWeekAdapter(daysOfWeek)
        binding.daysOfWeekRecyclerView.layoutManager = GridLayoutManager(requireContext(), 7)
        binding.daysOfWeekRecyclerView.adapter = daysOfWeekAdapter

        setupRecyclerView()

        binding.btBacktoDashboard.setOnClickListener(){
            findNavController().navigateUp()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupRecyclerView() {
        val days = mutableListOf<Day>()
        val firstDayOfMonth = currentMonth.withDayOfMonth(1)
        val daysInMonth = firstDayOfMonth.lengthOfMonth()

        val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
        for (i in 0 until firstDayOfWeek) {
            days.add(Day(LocalDate.now(), Color.TRANSPARENT))
        }

        coroutine.launch {
//            Log.d("test", "email: ${navArgs.email}")
//            Log.d("test", "before: ${before}")
//            Log.d("test", "after: ${after}")
            val calendarRequest = CalendarRequest(navArgs.email, before, after)
            val response = postRepository.getDates(calendarRequest)
//            Log.d("test","${response.body()}")
            var newList:List<CalendarResponse>? = response.body()
            if(newList.isNullOrEmpty()){

            }
            else{
               for(i in newList){
                   arrayTemp.add((i.date).substring(8,10))
//                   Log.d("test","${(i.date).substring(8,10)}")
               }

            }
            for (i in 1..daysInMonth) {
                val date = firstDayOfMonth.withDayOfMonth(i)
                var color = if(i == arrayTemp[2].toInt()){Color.RED}else{Color.WHITE}
                days.add(Day(date, color))
            }
//            for (i in 1..daysInMonth) {
//                val date = firstDayOfMonth.withDayOfMonth(i)
//                val color = when {
////                    response.body()!!.
////                    response.body()?.listTarget?.getOrNull(i - 1) == true -> Color.RED
//                    else -> Color.WHITE
//                }
//                days.add(Day(date, color))
//            }

            requireActivity().runOnUiThread {
                datesAdapter = DatesAdapter(days) { day ->
                    Toast.makeText(requireContext(), "${day.date}", Toast.LENGTH_SHORT).show()
                }
                binding.datesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 7)
                binding.datesRecyclerView.adapter = datesAdapter
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun changeMonth(monthOffset: Long) {
        currentMonth = currentMonth.plusMonths(monthOffset)
        binding.monthTextView.text = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        setupRecyclerView()
    }
}
