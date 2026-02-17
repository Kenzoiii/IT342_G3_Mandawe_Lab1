package com.it342.g3.mobile.api

import retrofit2.Call
import retrofit2.http.*

data class RegisterRequest(val username: String, val email: String, val password: String)

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(val message: String?, val token: String?, val username: String?, val role: String?)

data class MessageResponse(val message: String?)

data class UserProfile(val userId: Long?, val username: String?, val email: String?, val role: String?)

interface ApiService {
    @POST("/api/auth/register")
    fun register(@Body body: RegisterRequest): Call<MessageResponse>

    @POST("/api/auth/login")
    fun login(@Body body: LoginRequest): Call<LoginResponse>

    @GET("/api/user/me")
    fun me(@Header("Authorization") auth: String): Call<UserProfile>

    @POST("/api/auth/logout")
    fun logout(@Header("Authorization") auth: String): Call<MessageResponse>
}
