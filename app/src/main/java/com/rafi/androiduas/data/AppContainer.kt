package com.rafi.androiduas.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rafi.androiduas.service.TpqService
import com.rafi.androiduas.service.UserService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppContainer {
    val tpqRepository: TpqRepository
    val userRepository: UserRepository

}

class DefaultAppContainer() : AppContainer {
    private val baseUrl = "http://10.0.2.2:8080"
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .client(OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        ).build())
        .build()

    private val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    private val tpqService: TpqService by lazy {
        retrofit.create(TpqService::class.java)
    }

    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userService)
    }

    override val tpqRepository: TpqRepository by lazy {
        NetworkTpqRepository(tpqService)
    }

}