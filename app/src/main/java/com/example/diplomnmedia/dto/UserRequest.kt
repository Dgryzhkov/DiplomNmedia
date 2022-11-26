package com.example.diplomnmedia.dto

data class UserRequest(
    val id: Int = 0,
    val login: String = "",
    val name: String = "",
    val avatar: String = "",
    var checked: Boolean = false
)