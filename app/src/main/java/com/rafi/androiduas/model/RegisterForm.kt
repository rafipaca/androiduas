package com.rafi.androiduas.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterForm(
    val name: String,
    val nim: String,
    val email: String,
    val password: String
)