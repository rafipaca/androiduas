package com.rafi.androiduas.model

import kotlinx.serialization.Serializable

@Serializable
data class Tpq (
    val id: Long?,
    val name: String,
    val alamat: String,
    val nokontak: String,
    val createdOn: String? = null,
    val updatedOn: String? = null
)