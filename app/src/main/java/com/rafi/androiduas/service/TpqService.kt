package com.rafi.androiduas.service

import com.rafi.androiduas.model.CreateTpqForm
import com.rafi.androiduas.model.Tpq
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TpqService {

    @GET("/tpq-view")
    suspend fun getAllTpq(@Header("Authorization") token: String) : List<Tpq>

    @POST("/tpq-create")
    suspend fun createTpq(@Header("Authorization") token: String, @Body tpq: CreateTpqForm)

    @PUT("/tpq-update/{id}")
    suspend fun updateTpq(@Header("Authorization") token: String, @Path("id") tpqId: Long, @Body tpq : Tpq)

}