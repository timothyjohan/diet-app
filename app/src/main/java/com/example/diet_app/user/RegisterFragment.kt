package com.example.diet_app.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.diet_app.R
import com.example.diet_app.SosmedApplication
import com.example.diet_app.data.RegisterRequest
import com.example.diet_app.data.RegisterResponse
import com.example.diet_app.data.source.local.AppDatabase
import com.example.diet_app.databinding.FragmentRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var db: AppDatabase
    private val postRepository = SosmedApplication.postRepository
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
            register(email,password, name, gender)
            binding.name.text.clear()
            binding.email.text.clear()
            binding.password.text.clear()
            binding.confirm.text.clear()
            binding.rdmale.isChecked = false
            binding.rdfemale.isChecked = false
        }
        binding.btnLogin.setOnClickListener(){
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun register(email: String, password: String, name: String, gender: Boolean) {
        coroutine.launch {
            val registerRequest = RegisterRequest(email, password, name, gender)
            val response: Response<RegisterResponse> = SosmedApplication.postRepository.addUser(registerRequest)
            requireActivity().runOnUiThread {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    Toast.makeText(requireContext(), "Registered: ${registerResponse?.name}", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.loginFragment)
                } else {
                    Toast.makeText(requireContext(), "Failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}