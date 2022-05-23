package com.example.testapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.databinding.PostItemLayoutBinding
import com.example.testapplication.model.CommentModel

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    private val allCommentList = ArrayList<CommentModel>()

    class ViewHolder(private var binding: PostItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommentModel) {
            binding.apply {
                commentId.isVisible = true
                commentId.text = "Post Id: \n" + item.postId.toString()
                postId.text = "Name: \n" + item.name
                postTitle.text = "Email: \n" + item.email
                postBody.text = "Body: \n" + item.body
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PostItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = allCommentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return allCommentList.size
    }

    fun updateRecyclerData(commentList: List<CommentModel>) {
        allCommentList.clear()
        allCommentList.addAll(commentList)
        notifyDataSetChanged()
    }
}