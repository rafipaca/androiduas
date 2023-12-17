package com.rafi.androiduas.model

import kotlinx.serialization.Serializable

@Serializable
data class Role (
    val id: Long?,
    val name: String
)