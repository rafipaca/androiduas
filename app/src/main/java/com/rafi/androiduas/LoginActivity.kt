package com.rafi.androiduas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.rafi.androiduas.data.AppContainer
import com.rafi.androiduas.data.DefaultAppContainer
import com.rafi.androiduas.data.UserPreferencesRepository
import com.rafi.androiduas.data.UserRepository
import com.rafi.androiduas.model.LoginForm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {

    private lateinit var appContainer: AppContainer
    private lateinit var userRepository: UserRepository
    private lateinit var userPreferencesRepository: UserPreferencesRepository
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val application = application as MyApplication
        appContainer = DefaultAppContainer()
        userRepository = appContainer.userRepository // Inisialisasi userRepository
        userPreferencesRepository = application.userPreferencesRepository

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val emailField = emailEditText.text.toString()
            val passwordField = passwordEditText.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val loginResponse = userRepository.login(LoginForm(emailField, passwordField))
                    Log.d(TAG, "accessToken: ${loginResponse.accessToken}")

                    userPreferencesRepository.saveToken(loginResponse.accessToken)

                    val user = userRepository.getProfile(loginResponse.accessToken)
                    val isAdmin = user.roles?.any { role -> role.name == "ROLE_ADMIN" }
                    val isUser = user.roles?.any { role -> role.name == "ROLE_USER" }

                    Log.d(TAG, "name: ${user.name}")
                    Log.d(TAG, "email: ${user.email}")
                    Log.d(TAG, "isAdmin: $isAdmin")
                    Log.d(TAG, "isUser: $isUser")

                    userPreferencesRepository.saveName(user.name)
                    userPreferencesRepository.saveEmail(user.email)
                    userPreferencesRepository.saveIsAdmin(isAdmin ?: false)
                    userPreferencesRepository.saveIsUser(isUser ?: false)

                    withContext(Dispatchers.Main) {
                        // Pindah ke halaman beranda (HomeActivity) setelah login berhasil
                        //ujicoba
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        intent.putExtra("token", loginResponse.accessToken)
                        startActivity(intent)
                        finish()
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        // Tangani kesalahan yang terjadi selama login atau pengambilan profil
                        Log.e(TAG, "Error during login or fetching profile:", e)
                        // Tampilkan pesan kesalahan kepada pengguna atau lakukan tindakan yang sesuai
                        // Misalnya, tampilkan pesan kesalahan menggunakan Toast
                        // Toast.makeText(this@LoginActivity, "Login failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
