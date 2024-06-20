package com.example.diet_app.post

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diet_app.R
import com.example.diet_app.SosmedApplication
import com.example.diet_app.data.source.remote.MdpService
import com.example.diet_app.databinding.FragmentPostBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PostFragment : Fragment() {

    lateinit var binding: FragmentPostBinding
//    val viewModel:PostViewModel by viewModels<PostViewModel>()
    val navArgs:PostFragmentArgs by navArgs()
    private val postRepository = SosmedApplication.postRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_post,
            container,
            false
        )

//        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val classFood = ArrayList<ClassFood>()
        classFood.add(ClassFood("Ayam", 1234.0, 123.0, 123.0,4123.0))
        classFood.add(ClassFood("Ikan", 1234.0, 123.0, 123.0,4123.0))
        val adapter = FoodAdapter(classFood)
        binding.rvFood.adapter = adapter
        binding.rvFood.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val id = navArgs.postId
//        if(id >= 0){
//            viewModel.getPost(id)
//        }

        binding.titleEtPost.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                fetchAutocomplete(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })
    }
    private fun fetchAutocomplete(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
//                val response = postRepository.getAllPosts().getAllPosts(query)
                val response:List<String> = postRepository.getAllPosts(query = query)
//                val allFoodNames = response.common.map { it.food_name } + response.branded.map { it.food_name }

                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(this@PostFragment.requireContext(), android.R.layout.simple_spinner_item, response)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinner.adapter = adapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}