package com.example.diplomnmedia.repository.newEvent

import okhttp3.MultipartBody
import com.example.diplomnmedia.api.ApiService
import com.example.diplomnmedia.dao.EventDao
import com.example.diplomnmedia.dto.*
import com.example.diplomnmedia.entity.EventEntity
import com.example.diplomnmedia.enumeration.AttachmentType
import com.example.diplomnmedia.error.ApiError
import com.example.diplomnmedia.error.NetworkError
import java.io.IOException
import javax.inject.Inject


class NewEventRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: EventDao
) : NewEventRepository {
    override suspend fun loadUsers(): List<UserRequest> {
        val usersList: List<UserRequest>
        try {
            val response = apiService.getUsers()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            usersList = response.body() ?: throw ApiError(response.code(), response.message())
            return usersList
        } catch (e: IOException) {
            throw NetworkError
        }
    }

    override suspend fun addPictureToTheEvent(
        attachmentType: AttachmentType,
        image: MultipartBody.Part
    ): Attachment {
        try {
            val response = apiService.addMultimedia(image)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val mediaResponse =
                response.body() ?: throw ApiError(response.code(), response.message())
            val attachment = Attachment(mediaResponse.url, attachmentType)
            return attachment
        } catch (e: IOException) {
            throw NetworkError
        }
    }

    override suspend fun addEvent(event: EventRequest) {
        try {
            val response = apiService.addEvent(event)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            } else {
                val body = response.body() ?: throw ApiError(response.code(), response.message())
                dao.insert(EventEntity.fromDto(body))
            }
        } catch (e: IOException) {
            throw NetworkError
        }
    }

    override suspend fun getEvent(id: Int): EventRequest {
        try {
            val response = apiService.getEvent(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            } else {
                val body = response.body() ?: throw ApiError(response.code(), response.message())
                return EventRequest(
                    id = body.id,
                    content = body.content,
                    datetime = body.datetime,
                    coords = body.coords,
                    type = body.type,
                    attachment = body.attachment,
                    link = body.link,
                    speakerIds = body.speakerIds
                )
            }
        } catch (e: IOException) {
            throw NetworkError
        }
    }

    override suspend fun getUser(id: Int): UserRequest {
        try {
            val response = apiService.getUser(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            } else {
                return response.body() ?: throw ApiError(response.code(), response.message())
            }
        } catch (e: IOException) {
            throw NetworkError
        }
    }
}