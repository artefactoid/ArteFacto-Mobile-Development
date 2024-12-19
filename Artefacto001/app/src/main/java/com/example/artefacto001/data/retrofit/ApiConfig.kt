package com.example.artefacto001.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {

    enum class ApiEnvironment(val baseUrl: String) {
        ML_SERVICE("https://artefacto-ml-service-435581098670.asia-southeast2.run.app"),
        BACKEND_SERVICE("https://artefacto-backend-service-435581098670.asia-southeast2.run.app"),
        BACKEND_2("https://backend-2-435581098670.asia-southeast2.run.app")
    }

    fun getApiService(environment: ApiEnvironment): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(environment.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }

    fun getAuthApi(environment: ApiEnvironment): AuthApi {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(environment.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(AuthApi::class.java)
    }

    fun getTempleApi(): ApiServiceCandi {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(ApiEnvironment.BACKEND_SERVICE.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiServiceCandi::class.java)
    }

    fun getArtefactApi(): ApiServiceArtefact {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(ApiEnvironment.BACKEND_SERVICE.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiServiceArtefact::class.java)
    }
}



//// Memilih environment untuk ML Service
//val mlService = ApiConfig.getApiService(ApiConfig.ApiEnvironment.ML_SERVICE)
//private val mlService = ApiConfig.getApiService(ApiConfig.ApiEnvironment.ML_SERVICE)

//
//// Memilih environment untuk Backend Service (temple)
//val backendService = ApiConfig.getApiService(ApiConfig.ApiEnvironment.BACKEND_SERVICE)
//private val backendService = ApiConfig.getApiService(ApiConfig.ApiEnvironment.BACKEND_SERVICE)

//
//// Memilih environment untuk Backend Service (user, ticket, transaction, ownedticket)
//val backend2 = ApiConfig.getApiService(ApiConfig.ApiEnvironment.BACKEND_2)
//private val backend2 = ApiConfig.getApiService(ApiConfig.ApiEnvironment.BACKEND_2)