package com.example.diet_app.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import com.example.diet_app.data.Config
import com.example.diet_app.data.CurrentUser
import com.example.diet_app.data.source.local.AppDatabase
import com.example.diet_app.databinding.FragmentDashboardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var email: String
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var db: AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return  binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "DBCalorieCraft").fallbackToDestructiveMigration().build()

        coroutine.launch {
            try {
                val curr = db.currentDao().getUser()
                email = curr!!.email

                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Loaded ${email}", Toast.LENGTH_SHORT).show()
                    setupUI()
                }
            } catch (e: Exception) {
                val curr = CurrentUser(1,"dummy123", "dummy123", "dummy123", false)
                db.currentDao().insert(curr)
                email = "dummy123"
                val config = Config(1, false, false)
                db.configDao().insert(config)
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Registered ${email}", Toast.LENGTH_SHORT).show()
                    setupUI()
                }
            }
        }
    }

    private fun setupUI() {
        email?.let {
            if (it == "dummy123") {
                setupForDummyUser()
            } else {
                setupForRegisteredUser(it)
            }
        }
    } private fun setupForDummyUser() {
        binding.btSettings.setOnClickListener {
            navigateToLoginFragment()
        }
        binding.btnCalculator.setOnClickListener {
            navigateToLoginFragment()
        }
        binding.btnDaily.setOnClickListener {
            navigateToLoginFragment()
        }
        binding.btnFood.setOnClickListener {
            navigateToLoginFragment()
        }
    }

    private fun setupForRegisteredUser(email: String) {
        binding.btSettings.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToSettingsFragment(email)
            findNavController().navigate(action)
        }
    }

    private fun navigateToLoginFragment() {
        val action = DashboardFragmentDirections.actionDashboardFragmentToLoginFragment()
        findNavController().navigate(action)
    }
}