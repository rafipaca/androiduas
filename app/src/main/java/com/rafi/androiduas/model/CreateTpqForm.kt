package com.rafi.androiduas.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateTpqForm (
    val name: String,
    val alamat: String,
    val nokontak: String
)