package com.example.diplomnmedia.repository.usersWall


import androidx.lifecycle.MutableLiveData
import com.example.diplomnmedia.dto.Job
import com.example.diplomnmedia.dto.UserRequest

interface UserWallRepository {
    val data: MutableLiveData<List<Job>>
    val userData: MutableLiveData<UserRequest>
    suspend fun getJobUser(id: String)
    suspend fun getUser(id: Int)
}