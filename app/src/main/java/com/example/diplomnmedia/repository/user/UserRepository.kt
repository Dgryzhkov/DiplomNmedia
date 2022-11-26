package com.example.diplomnmedia.repository.user

import com.example.diplomnmedia.dto.UserRegistration
import com.example.diplomnmedia.dto.UserResponse

interface UserRepository {
    suspend fun onSignIn(userResponse: UserResponse)
    suspend fun onSignUp(user: UserRegistration)
}