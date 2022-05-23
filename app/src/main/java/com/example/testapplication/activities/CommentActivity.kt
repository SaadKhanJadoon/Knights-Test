package com.example.testapplication.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapplication.adapter.CommentAdapter
import com.example.testapplication.apicall.Resource
import com.example.testapplication.databinding.ActivityCommentBinding
import com.example.testapplication.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommentActivity : AppCompatActivity() {

    private var binding: ActivityCommentBinding? = null
    private val commentBinding get() = binding!!

    //View Model Object
    private val viewModel: ViewModel by viewModels()
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(commentBinding.root)
        supportActionBar?.title = "Comment Activity"

        val postId = intent.extras?.getString("postId")
        Toast.makeText(this, "Post Id $postId", Toast.LENGTH_SHORT).show()
        setupRecyclerView()

        CoroutineScope(Dispatchers.Main).launch {
            if (postId != null) {
                viewModel.getAllComment(postId).observe(this@CommentActivity) {
                    when (it.status) {
                        Resource.Status.SUCCESS -> {
                            commentAdapter.updateRecyclerData(it.data!!)
                        }
                        Resource.Status.FAILURE -> {
                            Toast.makeText(
                                this@CommentActivity,
                                "Failed to load the data ${it.message}",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                        Resource.Status.LOADING -> {
                            Toast.makeText(this@CommentActivity, "Loading...", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() = binding?.commentRecyclerView.apply {
        val layoutManager = LinearLayoutManager(this@CommentActivity)
        this?.layoutManager = layoutManager
        commentAdapter = CommentAdapter()
        this?.setHasFixedSize(true)
        this?.adapter = commentAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}