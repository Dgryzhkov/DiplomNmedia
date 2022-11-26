package com.example.diplomnmedia.repository.post

import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.diplomnmedia.api.ApiService
import com.example.diplomnmedia.dao.PostDao
import com.example.diplomnmedia.dto.PostResponse
import com.example.diplomnmedia.dto.UserRequest
import com.example.diplomnmedia.entity.PostEntity
import com.example.diplomnmedia.error.ApiError
import com.example.diplomnmedia.error.NetworkError
import java.io.IOException
import javax.inject.Inject

val emptyList = listOf<UserRequest>()

@OptIn(ExperimentalPagingApi::class)
class PostRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    mediator: PostRemoteMediator,
    private val dao: PostDao
) : PostRepository {

    override val data: Flow<PagingData<PostResponse>> =
        Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { dao.getAll() },
            remoteMediator = mediator
        ).flow.map {
            it.map(PostEntity::toDto)
        }


    override val dataUsersMentions: MutableLiveData<List<UserRequest>> = MutableLiveData(emptyList)


    override suspend fun loadUsersMentions(list: List<Int>) {
        val usersList = mutableListOf<UserRequest>()
        try {
            list.forEach {
                val response = apiService.getUser(it)
                if (!response.isSuccessful) {
                    throw ApiError(response.code(), response.message())
                }
                usersList.add(
                    response.body() ?: throw ApiError(
                        response.code(),
                        response.message()
                    )
                )
            }
            dataUsersMentions.postValue(usersList)
        } catch (e: IOException) {
            throw NetworkError
        }
    }

    override suspend fun removeById(id: Int) {
        try {
            val response = apiService.removeById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            dao.removeById(id)
        } catch (e: IOException) {
            throw NetworkError
        }
    }

    override suspend fun likeById(id: Int): PostResponse {
        try {
            val response = apiService.likeById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(PostEntity.fromDto(body))
            return body
        } catch (e: IOException) {
            throw NetworkError
        }
    }

    override suspend fun disLikeById(id: Int): PostResponse {
        try {
            val response = apiService.dislikeById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(PostEntity.fromDto(body))
            return body
        } catch (e: IOException) {
            throw NetworkError
        }
    }

    override suspend fun getPost(id: Int): PostResponse {
        try {
            val response = apiService.getPost(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            return response.body() ?: throw ApiError(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkError
        }
    }

}