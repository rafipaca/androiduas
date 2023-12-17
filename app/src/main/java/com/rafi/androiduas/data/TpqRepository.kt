package com.rafi.androiduas.data

import com.rafi.androiduas.model.CreateTpqForm
import com.rafi.androiduas.model.Tpq
import com.rafi.androiduas.service.TpqService

interface TpqRepository {
    suspend fun createTpq(token: String, tpq: CreateTpqForm)
    suspend fun getAllTpq(token: String): List<Tpq>
    suspend fun updateTpq(token: String, tpqId: Long, tpq: Tpq)
}

class NetworkTpqRepository(private val tpqService : TpqService) : TpqRepository {
    override suspend fun createTpq(token: String, tpq: CreateTpqForm) = tpqService.createTpq("Bearer $token", tpq)
    override suspend fun getAllTpq(token: String) = tpqService.getAllTpq("Bearer $token")
    override suspend fun updateTpq(token: String, tpqId: Long, tpq: Tpq) = tpqService.updateTpq("Bearer $token", tpqId, tpq)
}