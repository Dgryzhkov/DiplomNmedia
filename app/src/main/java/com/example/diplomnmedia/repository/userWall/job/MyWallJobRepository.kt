package com.example.diplomnmedia.repository.userWall.job

import androidx.lifecycle.MutableLiveData
import com.example.diplomnmedia.dto.Job

interface MyWallJobRepository {
    val dateJob: MutableLiveData<MutableList<Job>>
    suspend fun getMyJob()
    suspend fun removeById(id: Int)
    suspend fun save(job: Job)
}