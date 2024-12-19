package com.example.artefacto001.data.retrofit

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @Multipart
    @POST("/predict")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Call<FileUploadResponse>
}

interface ApiServiceCandi {
    @GET("temples")
    suspend fun getTemples(
        @Header("Authorization") authHeader: String
    ): Response<TempleResponse>
}

interface ApiServiceArtefact {
    @GET("temples/{templeID}/artifacts")
    suspend fun getArtifacts(
        @Path("templeID") templeID: Int,
        @Header("Authorization") token: String
    ): Response<ArtifactsResponse>
}

interface AuthApi {
    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}

data class LoginRequest(
    val email: String,
    val password: String
)
