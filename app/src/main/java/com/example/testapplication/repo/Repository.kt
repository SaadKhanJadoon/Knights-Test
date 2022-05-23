package com.example.testapplication.repo

import com.example.testapplication.apicall.ApiInterface
import javax.inject.Inject

class Repository @Inject constructor(private val apiInterface: ApiInterface) {

    //Get All Posts
    suspend fun getAllPost() = apiInterface.getAllPost()

    //Get All Comments
    suspend fun getAllComment(postId: String) = apiInterface.getAllComments(postId)
}