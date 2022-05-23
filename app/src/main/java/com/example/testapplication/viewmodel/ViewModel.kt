package com.example.testapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.testapplication.apicall.Resource
import com.example.testapplication.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {
    //Get All Posts from repo
    fun getAllPost() = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getAllPost()))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message.toString()))
        }
    }

    //Get All Posts from repo
    fun getAllComment(postId: String) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getAllComment(postId)))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message.toString()))
        }
    }
}