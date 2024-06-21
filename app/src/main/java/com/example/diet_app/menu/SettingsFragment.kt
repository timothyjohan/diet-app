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
import com.example.diet_app.SosmedApplication
import com.example.diet_app.data.Config
import com.example.diet_app.data.CurrentUser
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
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val navArgs: SettingsFragmentArgs by navArgs<SettingsFragmentArgs>()
        email = navArgs.email

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "DBCalorieCraft").fallbackToDestructiveMigration().build()

        coroutine.launch {
            val existingUser = db.configDao().getConfigByEmail()
            requireActivity().runOnUiThread {
                binding.swDaily.isChecked = existingUser!!.dailyReminder
                binding.swWeekly.isChecked = existingUser.weeklyReminder
            }

        }
        binding.btSaveSettings.setOnClickListener {
            coroutine.launch {
                val data = Config(1, binding.swDaily.isChecked, binding.swWeekly.isChecked)
                return@launch db.configDao().update(data)
            }
            Toast.makeText(requireContext(), "Settings updated", Toast.LENGTH_SHORT).show()
        }

        binding.btToDashboard.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btDelete.setOnClickListener {
            coroutine.launch {
//                val existingUser = db.userDao().getUser(email)
//                val user = User(existingUser!!.email, existingUser.password, existingUser.name, existingUser.gender)
//                db.userDao().delete(user)
                db.currentDao().update(CurrentUser(1, "dummy123", "dummy123", "dummy123", true))
                SosmedApplication.postRepository.deleteUser(email)
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Account has been deleted", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }

        binding.btLogout.setOnClickListener(){
            coroutine.launch {
                db.currentDao().update(CurrentUser(1, "dummy123", "dummy123", "dummy123", true))
                requireActivity().runOnUiThread(){
                    Toast.makeText(requireContext(), "Log out", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

}