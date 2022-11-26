package com.example.diplomnmedia.repository.userWall.post

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.example.diplomnmedia.dto.PostResponse
import com.example.diplomnmedia.dto.UserRequest

interface MyWallPostRepository {
    val data: Flow<PagingData<PostResponse>>
    val dataUsersMentions: MutableLiveData<List<UserRequest>>
    suspend fun loadUsersMentions(list: List<Int>)
    suspend fun removeById(id: Int)
    suspend fun likeById(id: Int)
    suspend fun disLikeById(id: Int)
    suspend fun removeAll()
}