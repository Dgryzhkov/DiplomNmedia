package com.example.diplomnmedia.error

import android.database.SQLException
import com.example.diplomnmedia.R
import java.io.IOException

sealed class AppError(var code: String): RuntimeException() {
    companion object {
        fun from(e: Throwable): AppError = when (e) {
            is AppError -> e
            is SQLException -> DbError
            is IOException -> NetworkError
            else -> UnknownError
        }
    }
}
class ApiError(val status: Int, code: String): AppError(code)
object DbError : AppError((R.string.error_db).toString())
object NetworkError : AppError((R.string.error_network).toString())
object UnknownError: AppError((R.string.error_unknown).toString())