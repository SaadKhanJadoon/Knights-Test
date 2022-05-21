package com.example.testapplication.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.testapplication.R
import com.example.testapplication.adapter.ImageAdapter
import com.example.testapplication.databinding.ActivityMainBinding
import com.example.testapplication.model.ImageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val mainBinding get() = binding!!
    private lateinit var imageList: ArrayList<ImageModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        supportActionBar?.title = "Main Activity"

        imageList = arrayListOf()
        CoroutineScope(Dispatchers.Main).launch {
            imageList.add(0, ImageModel(R.drawable.theme3, 1))
            imageList.add(1, ImageModel(R.drawable.theme3, 1))
            imageList.add(2, ImageModel(R.drawable.ic_launcher_foreground, 2))
            imageList.add(3, ImageModel(R.drawable.theme3, 1))
            imageList.add(4, ImageModel(R.drawable.theme3, 1))
        }.invokeOnCompletion {
            val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            binding?.apply {
                imageRecyclerView.layoutManager = layoutManager
                val themeAdapter = ImageAdapter(this@MainActivity, imageList)
                imageRecyclerView.setHasFixedSize(true)
                imageRecyclerView.adapter = themeAdapter
            }
        }

        binding?.apply {
            postActivity.setOnClickListener {
                val mainIntent = Intent(this@MainActivity, PostsActivity::class.java)
                startActivity(mainIntent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}