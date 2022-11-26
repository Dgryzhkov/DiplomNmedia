package com.example.diplomnmedia.repository.user

import okhttp3.RequestBody.Companion.toRequestBody
import com.example.diplomnmedia.api.ApiService
import com.example.diplomnmedia.auth.AppAuth
import com.example.diplomnmedia.dto.UserRegistration
import com.example.diplomnmedia.dto.UserResponse
import com.example.diplomnmedia.error.ApiError
import com.example.diplomnmedia.error.NetworkError
import com.example.diplomnmedia.error.UnknownError

import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val appAuth: AppAuth
) : UserRepository {

    override suspend fun onSignIn(userResponse: UserResponse) {
        try {
            val response = apiService.onSignIn(userResponse.login, userResponse.password)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val authState = response.body() ?: throw ApiError(response.code(), response.message())
            val token = authState.token ?: throw UnknownError
            val idUser = authState.id.toInt()
            val responseUser = apiService.getUser(idUser)
            if (!responseUser.isSuccessful) {
                throw ApiError(responseUser.code(), responseUser.message())
            }
            val avatarUser = responseUser.body()?.avatar
            val name = responseUser.body()?.name
            appAuth.setAuth(authState.id, token, avatarUser, name)
        } catch (e: IOException) {
            throw NetworkError
        }
    }

    override suspend fun onSignUp(user: UserRegistration) {
        val login = user.login.toRequestBody()
        val password = user.password.toRequestBody()
        val nameUser = user.name.toRequestBody()
        try {
            val response = if (user.file != null) {
                apiService.onSignUpHasAva(login, password, nameUser, user.file)
            } else {
                apiService.onSignUpNoAva(user.login, user.password, user.name, null)
            }
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val authState = response.body() ?: throw ApiError(response.code(), response.message())

            val token = authState.token ?: throw UnknownError
            val idUser = authState.id.toInt()
            val responseUser = apiService.getUser(idUser)
            if (!responseUser.isSuccessful) {
                throw ApiError(responseUser.code(), responseUser.message())
            }
            val avatarUser = responseUser.body()?.avatar
            val name = responseUser.body()?.name
            appAuth.setAuth(authState.id, token, avatarUser, name)
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }
}