package com.example.diplomnmedia.dto

import okhttp3.MultipartBody

data class UserRegistration(
    val login: String,
    val password: String,
    val name: String,
    val file: MultipartBody.Part?
)