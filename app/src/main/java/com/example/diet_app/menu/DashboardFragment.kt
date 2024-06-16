package com.example.diet_app.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.diet_app.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var email: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val navArgs: DashboardFragmentArgs by navArgs<DashboardFragmentArgs>()
        email = navArgs.email.toString()

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btSettings.setOnClickListener(){
            val action = DashboardFragmentDirections.actionDashboardFragmentToSettingsFragment(email)
            findNavController().navigate(action)
        }
    }

}