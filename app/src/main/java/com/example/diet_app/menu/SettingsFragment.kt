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
import com.example.diet_app.ClassConfig
import com.example.diet_app.data.Config
import com.example.diet_app.data.User
import com.example.diet_app.data.source.local.AppDatabase
import com.example.diet_app.databinding.FragmentSettingsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private val coroutine = CoroutineScope(Dispatchers.IO)
    lateinit var binding: FragmentSettingsBinding
    private lateinit var email: String
//    private lateinit var classConfig: ClassConfig
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val navArgs: SettingsFragmentArgs by navArgs<SettingsFragmentArgs>()
        email = navArgs.email

//        classConfig = ClassConfig(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "DBCalorieCraft").fallbackToDestructiveMigration().build()

        coroutine.launch {
            val existingUser = db.configDao().getConfigByEmail(email)
            requireActivity().runOnUiThread {
                binding.swDaily.isChecked = existingUser!!.dailyReminder
                binding.swWeekly.isChecked = existingUser.weeklyReminder
            }

        }
//            classConfig.dailyReminder = binding.swDaily.isChecked
//            classConfig.weeklyReminder = binding.swWeekly.isChecked
        binding.btSaveSettings.setOnClickListener {
            coroutine.launch {
                val data = Config(email, binding.swDaily.isChecked, binding.swWeekly.isChecked)
                return@launch db.configDao().update(data)
            }
            Toast.makeText(requireContext(), "Settings updated", Toast.LENGTH_SHORT).show()
        }

        binding.btToDashboard.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToDashboardFragment(email)
            findNavController().navigate(action)
        }

        binding.btDelete.setOnClickListener {
        coroutine.launch {
            val existingUser = db.userDao().getUser(email)
            requireActivity().runOnUiThread {
                Toast.makeText(requireContext(), "Account has been deleted", Toast.LENGTH_SHORT).show()
            }
            val user = User(existingUser!!.email, existingUser.password, existingUser.name, existingUser.gender)
            db.userDao().delete(user)
            val existingSettings = db.configDao().getConfigByEmail(email)
            val config = Config(email, existingSettings!!.dailyReminder, existingSettings.weeklyReminder)
            db.configDao().delete(config)
        }
            val action = SettingsFragmentDirections.actionSettingsFragmentToLoginFragment2()
            findNavController().navigate(action)
        }
    }

}