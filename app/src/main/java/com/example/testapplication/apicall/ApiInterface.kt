package com.example.testapplication.apicall

import com.example.testapplication.model.CommentModel
import com.example.testapplication.model.PostModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("posts")
    suspend fun getAllPost(): List<PostModel>

    @GET("comments/")
    suspend fun getAllComments(
        @Query("postId") postId: String,
    ): List<CommentModel>
}