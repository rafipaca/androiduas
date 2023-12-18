package com.rafi.androiduas

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rafi.androiduas.R
import com.rafi.androiduas.data.AppContainer
import com.rafi.androiduas.data.DefaultAppContainer
import com.rafi.androiduas.data.UserPreferencesRepository
import com.rafi.androiduas.data.UserRepository
import com.rafi.androiduas.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditUserActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository

    private lateinit var token: String
    private lateinit var currentUser: User
    private lateinit var txtNamaLengkap: TextInputEditText
    private lateinit var txtNim: TextInputEditText
    private lateinit var txtEmail: TextInputEditText
    private lateinit var btnEditAccount: Button
    private lateinit var userPreferencesRepository : UserPreferencesRepository
    private var appContainer: AppContainer = DefaultAppContainer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)
        val application = application as MyApplication
        appContainer = DefaultAppContainer()
        userRepository = appContainer.userRepository
        userPreferencesRepository = application.userPreferencesRepository


        // Initialize views
        txtNamaLengkap = findViewById(R.id.txtNamaLengkap)
        txtNim = findViewById(R.id.txtNim)
        txtEmail = findViewById(R.id.txtEmail)
        btnEditAccount = findViewById(R.id.btnEditAccount)

        // Set up click listener for the Edit button
        btnEditAccount.setOnClickListener {
            updateProfile()
        }

        // Load user data for editing
        loadUserProfile()
        init()
    }

    fun init() {
        // Dapatkan informasi pengguna dan inisialisasi userState
        lifecycleScope.launch {
            try {
                userPreferencesRepository.user.collect { user ->
                    token = user.token

                }
            } catch (e: Exception) {

            }
        }
    }

    private fun loadUserProfile() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                 currentUser = withContext(Dispatchers.IO) {
                    // You need to implement a function to load the user profile based on the user's ID or other identifier
                    // For now, let's assume you have a function in UserRepository to get the current user's profile
                    userRepository.getProfile(token)
                }
                displayUserProfile(currentUser)
            } catch (e: Exception) {
                // Handle the exception, such as showing an error message
                e.printStackTrace()
            }
        }
    }


    private fun displayUserProfile(user: User) {
        txtNamaLengkap.setText(user.name)
        txtNim.setText(user.nim)
        txtEmail.setText(user.email)
        // Set other fields accordingly
    }

    private fun updateProfile() {
        // Get updated user data from the input fields
        val updatedUser = User(
            id = currentUser.id,  // Pastikan untuk mendapatkan ID dari user yang sedang diperbarui
            password = currentUser.password,  // Tetapkan nilai password yang ada
            roles = currentUser.roles,  // Tetapkan nilai roles yang ada
            name = txtNamaLengkap.text.toString(),
            nim = txtNim.text.toString(),
            email = txtEmail.text.toString()
            // Set other fields accordingly
        )


        lifecycleScope.launch {
            try {
                // Gantilah "token" dengan cara mendapatkan token autentikasi yang sesuai dengan implementasi Anda
                userRepository.updateProfile(token, updatedUser)
                Toast.makeText(this@EditUserActivity, "Berhasil Mengganti Profile!", Toast.LENGTH_SHORT).show()
                finish() // Tutup activity setelah berhasil menambahkan TPQ
            } catch (e: Exception) {
                Toast.makeText(this@EditUserActivity, "Gagal Mengganti Profile!", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }
}
