package com.example.diet_app.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.diet_app.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
    binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val navArgs: DashboardFragmentArgs by navArgs<DashboardFragmentArgs>()
        val email = navArgs.email



        return binding.root
    }

}