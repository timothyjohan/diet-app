package com.example.diet_app.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.diet_app.R
import com.example.diet_app.data.Post
import com.example.diet_app.databinding.FragmentPostBinding


class PostFragment : Fragment() {

    lateinit var binding: FragmentPostBinding
    val viewModel:PostViewModel by viewModels<PostViewModel>()
    val navArgs:PostFragmentArgs by navArgs()

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

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navArgs.postId
        if(id >= 0){
            viewModel.getPost(id)
        }

        binding.cancelBtnPost.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.processBtnPost.setOnClickListener {
            val title = binding.titleEtPost.text.toString()
            val content = binding.contentEtPost.text.toString()

            if(id >= 0){
                viewModel.updatePost(title, content)
            }
            else{
                viewModel.createPost(title, content)
            }

            findNavController().popBackStack()
        }
    }
}