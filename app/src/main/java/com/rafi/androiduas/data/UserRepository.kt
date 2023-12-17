package com.rafi.androiduas.data

import com.rafi.androiduas.model.ChangePasswordForm
import com.rafi.androiduas.model.LoginForm
import com.rafi.androiduas.model.LoginResponse
import com.rafi.androiduas.model.RegisterForm
import com.rafi.androiduas.model.User
import com.rafi.androiduas.service.UserService

interface UserRepository {
    suspend fun register(user: RegisterForm)
    suspend fun login(form: LoginForm): LoginResponse
    suspend fun getProfile(token: String): User
    suspend fun updateProfile(token: String, user: User): User
    suspend fun updatePassword(token: String, form: ChangePasswordForm): User
    suspend fun deleteProfile(token: String)
    suspend fun getAllUsers(token: String): List<User>
    suspend fun deleteUser(token: String, id: Long)
    suspend fun getUserById(token: String, id: Long): User
    suspend fun addRoleToUser(token: String, userId: Long, role: String)
    suspend fun removeRoleFromUser(token: String, userId: Long, role: String)
}

class NetworkUserRepository(private val userService: UserService) : UserRepository {
    override suspend fun register(user: RegisterForm) = userService.register(user)
    override suspend fun login(form: LoginForm): LoginResponse = userService.login(form)
    override suspend fun getProfile(token: String): User = userService.getProfile("Bearer $token")
    override suspend fun updateProfile(token: String, user: User): User = userService.updateProfile("Bearer $token", user)
    override suspend fun updatePassword(token: String, form: ChangePasswordForm): User = userService.updatePassword("Bearer $token", form)
    override suspend fun deleteProfile(token: String) = userService.deleteProfile("Bearer $token")
    override suspend fun getAllUsers(token: String) = userService.getAllUsers("Bearer $token")
    override suspend fun deleteUser(token: String, id: Long) = userService.deleteUser("Bearer $token", id)
    override suspend fun getUserById(token: String, id: Long) = userService.getUserById("Bearer $token", id)
    override suspend fun addRoleToUser(token: String, userId: Long, role: String) = userService.addRoleToUser("Bearer $token", userId, role)
    override suspend fun removeRoleFromUser(token: String, userId: Long, role: String) = userService.removeRoleFromUser("Bearer $token", userId, role)
}