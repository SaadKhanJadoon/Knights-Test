package com.example.testapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapplication.AppClass
import com.example.testapplication.adapter.PostAdapter
import com.example.testapplication.apicall.Resource
import com.example.testapplication.databinding.ActivityPostsBinding
import com.example.testapplication.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsActivity : AppCompatActivity() {

    private var binding: ActivityPostsBinding? = null
    private val postBinding get() = binding!!

    //View Model Object
    private val viewModel: ViewModel  by viewModels()
    lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostsBinding.inflate(layoutInflater)
        setContentView(postBinding.root)
        supportActionBar?.title = "Post Activity"
        setupRecyclerView()

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getAllPost().observe(this@PostsActivity) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        postAdapter.updateRecyclerData(it.data!!)
                    }
                    Resource.Status.FAILURE -> {
                        Toast.makeText(
                            this@PostsActivity,
                            "Failed to load the data ${it.message}",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                    Resource.Status.LOADING -> {
                        Toast.makeText(this@PostsActivity, "Loading...", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() = binding?.postsRecyclerView.apply {
        val layoutManager = LinearLayoutManager(this@PostsActivity)
        this?.layoutManager = layoutManager
        postAdapter = PostAdapter()
        this?.setHasFixedSize(true)
        this?.adapter = postAdapter

        postAdapter.setOnItemClickListener(object : PostAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, id: Int?) {
                val mainIntent = Intent(this@PostsActivity, CommentActivity::class.java)
                mainIntent.putExtra("postId", id.toString())
                startActivity(mainIntent)
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}