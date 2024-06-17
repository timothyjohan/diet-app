package com.example.diet_app.user

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
import com.example.diet_app.databinding.FragmentLoginBinding
import com.example.diet_app.data.User
import com.example.diet_app.data.source.local.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private var users: ArrayList<User> = ArrayList()
    private lateinit var db: AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "DBCalorieCraft").fallbackToDestructiveMigration().build()

        binding.btnLogin.setOnClickListener(){
            if(binding.email.text.toString()=="" || binding.password.text.toString()==""){
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }else{
                val email = binding.email.text.toString()
                val password = binding.password.text.toString()
                coroutine.launch {
                    val existingUser = db.userDao().validUser(email, password)
                    if (existingUser != null) {
                        val newUser = CurrentUser(1,existingUser.email, existingUser.password, existingUser.name, existingUser.gender)
                        db.currentDao().update(newUser)
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Login Berhasil", Toast.LENGTH_SHORT).show()
                            val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment(email)
                            findNavController().navigate(action)
                        }
                    } else {
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Username/Password was wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            binding.email.text.clear()
            binding.password.text.clear()
        }
        binding.btnRegister.setOnClickListener(){
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }


}