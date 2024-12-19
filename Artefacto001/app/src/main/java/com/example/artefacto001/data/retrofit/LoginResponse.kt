package com.example.artefacto001.data.retrofit

data class LoginResponse(
    val status: String,
    val message: String,
    val data: DataLogin
)

data class DataLogin(
    val user: User,
    val jwtToken: String
)

data class User(
    val userID: Int,
    val username: String,
    val email: String,
    val createdAt: String,
    val updateAt: String
)
