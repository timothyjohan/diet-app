package com.example.diet_app.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.diet_app.R
import com.example.diet_app.data.User
import com.example.diet_app.data.source.local.AppDatabase
import com.example.diet_app.databinding.FragmentTutorialBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class Tutorial : Fragment() {
    private lateinit var binding: FragmentTutorialBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private var users: ArrayList<User> = ArrayList()
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTutorialBinding.inflate(inflater, container, false)
        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "DBCalorieCraft")
            .fallbackToDestructiveMigration().build()

        // Set up the OnClickListener for the btnBack button
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.dashboardFragment)
        }


        return binding.root
    }
}
