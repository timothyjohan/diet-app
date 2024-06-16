package com.example.diet_app.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.diet_app.data.User
import com.example.diet_app.data.source.local.AppDatabase
import com.example.diet_app.databinding.FragmentRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private var users: ArrayList<User> = ArrayList()
    private lateinit var db: AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "DBCalorieCraft").fallbackToDestructiveMigration().build()

        binding.btnRegister.setOnClickListener(){
            if (listOf(binding.name.text, binding.password.text, binding.password.text, binding.confirm.text).any { it.isEmpty() }) {
                Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.password.text.toString() != binding.confirm.text.toString()) {
                Toast.makeText(requireContext(), "Password dan Konfirmasi Password harus sama", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!binding.rdfemale.isChecked && !binding.rdmale.isChecked) {
                Toast.makeText(requireContext(), "Gender should be fill", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val gender = binding.rdmale.isChecked
            coroutine.launch {
                val existingUser = db.userDao().getUser(email)
                if (existingUser != null) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Username sudah digunakan, pilih username lain", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val user = User(email, password, name, gender)
                    db.userDao().insert(user)
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            binding.name.text.clear()
            binding.email.text.clear()
            binding.password.text.clear()
            binding.confirm.text.clear()
            binding.rdmale.isChecked = false
            binding.rdfemale.isChecked = false
        }
        binding.btnLogin.setOnClickListener(){
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}