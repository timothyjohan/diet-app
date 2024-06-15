package com.example.diet_app.timeline

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_app.R
import com.example.diet_app.data.Post
import com.example.diet_app.databinding.TimelineListItemBinding

class PostDiffUtil: DiffUtil.ItemCallback<Post>(){
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id &&
                oldItem.title == newItem.title &&
                oldItem.content == newItem.content
    }
}

class PostAdapter(
    var onEditClickListener:((Post)->Unit)? = null,
    var onDeleteClickListener:((Post)->Unit)? = null
) :ListAdapter<Post, PostAdapter.ViewHolder>(PostDiffUtil()) {
    class ViewHolder(val binding: TimelineListItemBinding): RecyclerView.ViewHolder(binding.root){
        val titleTv: TextView = binding.titleTvTimelineLi
        val contentTv: TextView = binding.contentTvTimelineLi
        val editBtn: Button = binding.editBtnTimelineLi
        val deleteBtn: Button = binding.deleteBtnTimelineLi
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val layout = LayoutInflater.from(parent.context).inflate(
//            R.layout.student_list_item, parent, false)
        val binding:TimelineListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.timeline_list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.titleTv.text = post.title
        holder.contentTv.text = post.content
        holder.editBtn.setOnClickListener {
            onEditClickListener?.invoke(post)
        }
        holder.deleteBtn.setOnClickListener {
            onDeleteClickListener?.invoke(post)
        }
    }
}