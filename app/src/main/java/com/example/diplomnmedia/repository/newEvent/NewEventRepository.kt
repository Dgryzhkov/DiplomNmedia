package com.example.diplomnmedia.repository.newEvent

import okhttp3.MultipartBody
import com.example.diplomnmedia.dto.Attachment
import com.example.diplomnmedia.dto.EventRequest
import com.example.diplomnmedia.dto.UserRequest
import com.example.diplomnmedia.enumeration.AttachmentType

interface NewEventRepository {
    suspend fun loadUsers(): List<UserRequest>
    suspend fun addPictureToTheEvent(
        attachmentType: AttachmentType,
        image: MultipartBody.Part
    ): Attachment

    suspend fun addEvent(event: EventRequest)
    suspend fun getEvent(id: Int): EventRequest
    suspend fun getUser(id: Int): UserRequest
}