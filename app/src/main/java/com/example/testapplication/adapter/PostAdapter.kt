package com.example.testapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.databinding.PostItemLayoutBinding
import com.example.testapplication.model.PostModel

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private val allPostList = ArrayList<PostModel>()
    private var mListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    class ViewHolder(private var binding: PostItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostModel, mListener: OnItemClickListener?) {
            binding.apply {
                commentId.isVisible = false
                postId.text = "Post Id: \n" + item.id.toString()
                postTitle.text = "Title: \n" + item.title
                postBody.text = "Body: \n" + item.body
                postClick.setOnClickListener { mListener?.onItemClick(adapterPosition, item.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PostItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return allPostList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = allPostList[position]
        holder.bind(item, mListener)
    }

    fun updateRecyclerData(postList: List<PostModel>) {
        allPostList.clear()
        allPostList.addAll(postList)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, id: Int?)
    }
}