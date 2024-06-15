package com.example.diet_app.timeline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_app.R
import com.example.diet_app.data.Post
import com.example.diet_app.databinding.FragmentTimelineBinding
import com.example.diet_app.timeline.TimelineFragmentDirections

class TimelineFragment : Fragment() {

    lateinit var binding:FragmentTimelineBinding
    val viewModel: TimelineViewModel by viewModels<TimelineViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_timeline,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val postAdapter = PostAdapter(
            {
                val action = TimelineFragmentDirections.actionTimelineFragmentToPostFragment(it.id)
                findNavController().navigate(action)
                viewModel.getPosts()
            },
            {
                viewModel.deletePost(it)
            }
        )
        binding.timelineRvTimeline.adapter = postAdapter

        val postsObserver:Observer<List<Post>> = Observer {
            postAdapter.submitList(it)
        }
        viewModel.posts.observe(viewLifecycleOwner, postsObserver)

        viewModel.getPosts()

        binding.timelineRvTimeline.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.timelineRvTimeline.addItemDecoration(
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        )

        binding.addBtnTimeline.setOnClickListener {
            val action = TimelineFragmentDirections.actionTimelineFragmentToPostFragment(-1)
            findNavController().navigate(action)
            viewModel.getPosts()
        }

        binding.refreshBtnTimeline.setOnClickListener {
            viewModel.getPosts(true)
        }
    }

}