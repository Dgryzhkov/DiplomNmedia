package com.example.diplomnmedia.repository.post

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.example.diplomnmedia.dto.PostResponse
import com.example.diplomnmedia.dto.UserRequest

interface PostRepository {
    val data: Flow<PagingData<PostResponse>>
    val dataUsersMentions: MutableLiveData<List<UserRequest>>
    suspend fun loadUsersMentions(list: List<Int>)
    suspend fun removeById(id: Int)
    suspend fun likeById(id: Int): PostResponse
    suspend fun disLikeById(id: Int): PostResponse
    suspend fun getPost(id: Int): PostResponse
}