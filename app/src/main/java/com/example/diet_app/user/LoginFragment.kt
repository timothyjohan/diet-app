package com.example.diet_app.user

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
import com.example.diet_app.SosmedApplication
import com.example.diet_app.data.CurrentUser
import com.example.diet_app.data.LoginRequest
import com.example.diet_app.databinding.FragmentLoginBinding
import com.example.diet_app.data.User
import com.example.diet_app.data.source.local.AppDatabase
import com.example.diet_app.data.source.remote.MdpService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.CacheResponse

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private var users: ArrayList<User> = ArrayList()
    private lateinit var db: AppDatabase
    private val postRepository = SosmedApplication.postRepository
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
                val loginRequest = LoginRequest(email, password)
                val handler = Handler(Looper.getMainLooper())
                Log.d("login", loginRequest.email)
                Log.d("login", loginRequest.password)


                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = postRepository.userLogin(loginRequest = loginRequest)
                        Log.d("login",response.toString())
                        if(response.code() == 401){
                            handler.post {
                                Toast.makeText(requireContext(), "Username or password incorrect", Toast.LENGTH_SHORT).show()


                            }
                        }else{
                            handler.post {
//                                Toast.makeText(requireContext(), "Username or password incorrect", Toast.LENGTH_SHORT).show()
                                Toast.makeText(requireContext(), "Logged in", Toast.LENGTH_SHORT).show()
                                val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment(binding.email.text.toString())
                                findNavController().navigate(action)

                            }

                        }
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }



//                coroutine.launch {
//                    val existingUser = db.userDao().validUser(email, password)
//                    if (existingUser != null) {
//                        val newUser = CurrentUser(1,existingUser.email, existingUser.password, existingUser.name, existingUser.gender)
//                        db.currentDao().update(newUser)
//                        requireActivity().runOnUiThread {
//                            Toast.makeText(requireContext(), "Login Berhasil", Toast.LENGTH_SHORT).show()
//                            val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment(email)
//                            findNavController().navigate(action)
//                        }
//                    } else {
//                        requireActivity().runOnUiThread {
//                            Toast.makeText(requireContext(), "Username/Password was wrong", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
            }
//            binding.email.text.clear()
//            binding.password.text.clear()
        }
        binding.btnRegister.setOnClickListener(){
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }


}