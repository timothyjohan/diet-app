package com.example.diet_app.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.diet_app.data.CurrentUser
import com.example.diet_app.data.User
import com.example.diet_app.data.source.local.AppDatabase
import com.example.diet_app.databinding.FragmentCalculatorBinding
import com.example.diet_app.databinding.FragmentLogFoodBinding
import com.example.diet_app.databinding.FragmentRecapWeeklyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecapWeeklyFragment : Fragment() {
    private lateinit var binding: FragmentRecapWeeklyBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private var users: ArrayList<User> = ArrayList()
    private lateinit var db: AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecapWeeklyBinding.inflate(inflater, container, false)
        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "DBCalorieCraft").fallbackToDestructiveMigration().build()


        return binding.root
    }


}