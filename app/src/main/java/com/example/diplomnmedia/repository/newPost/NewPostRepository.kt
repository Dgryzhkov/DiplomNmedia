package com.example.diplomnmedia.repository.newPost

import okhttp3.MultipartBody
import com.example.diplomnmedia.dto.Attachment
import com.example.diplomnmedia.dto.PostRequest
import com.example.diplomnmedia.dto.UserRequest
import com.example.diplomnmedia.enumeration.AttachmentType

interface NewPostRepository {
    suspend fun loadUsers(): List<UserRequest>
    suspend fun addPictureToThePost(
        attachmentType: AttachmentType,
        image: MultipartBody.Part
    ): Attachment

    suspend fun addPost(post: PostRequest)
    suspend fun getPost(id: Int): PostRequest
    suspend fun getUser(id: Int): UserRequest
}