package com.rafi.androiduas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rafi.androiduas.data.AppContainer
import com.rafi.androiduas.data.DefaultAppContainer
import com.rafi.androiduas.data.NetworkUserRepository
import com.rafi.androiduas.data.UserRepository
import com.rafi.androiduas.model.RegisterForm
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var txtNamaSignUp: TextInputEditText
    private lateinit var txtEmailSignUp: TextInputEditText
    private lateinit var txtNimSignUp: TextInputEditText
    private lateinit var txtPasswordSignUp: TextInputEditText
    private lateinit var txtConfirmSignUp: TextInputEditText

    private lateinit var txtLayoutNamaSignUp: TextInputLayout
    private lateinit var txtLayoutEmailSignUp: TextInputLayout
    private lateinit var txtLayoutNimSignUp: TextInputLayout
    private lateinit var txtLayoutPasswordSignUp: TextInputLayout
    private lateinit var txtLayoutConfirmSignUp: TextInputLayout

    private lateinit var appContainer: AppContainer
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        appContainer = DefaultAppContainer()
        userRepository = appContainer.userRepository // Inisialisasi userRepository

        // Initialize your views
        txtNamaSignUp = findViewById(R.id.txtNamaSignUp)
        txtNimSignUp = findViewById(R.id.txtNimSignUp)
        txtEmailSignUp = findViewById(R.id.txtEmailSignUp)
        txtPasswordSignUp = findViewById(R.id.txtPasswordSignUp)
        txtConfirmSignUp = findViewById(R.id.txtConfirmSignUp)

        txtLayoutNamaSignUp = findViewById(R.id.txtLayoutNamaSignUp)
        txtLayoutNimSignUp = findViewById(R.id.txtLayoutNimSignUp)
        txtLayoutEmailSignUp = findViewById(R.id.txtLayoutEmailSignUp)
        txtLayoutPasswordSignUp = findViewById(R.id.txtLayoutPasswordSignUp)
        txtLayoutConfirmSignUp = findViewById(R.id.txtLayoutConfrimSignUp)

        val btnSignUp: Button = findViewById(R.id.btnSignUp)
        btnSignUp.setOnClickListener {
            performSignUp()
        }

    }

    private fun performSignUp() {
        val name = txtNamaSignUp.text.toString()
        val nim = txtNimSignUp.text.toString()
        val email = txtEmailSignUp.text.toString()
        val password = txtPasswordSignUp.text.toString()
        val confirmPassword = txtConfirmSignUp.text.toString()

        // Validate input
        if (name.isEmpty() || nim.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            // Handle empty fields
            return
        }

//        if (password != confirmPassword) {
//            // Handle password mismatch
//            return
//        }

        val registerForm = RegisterForm(name, nim, email, password)

        // Use lifecycleScope to perform the registration asynchronously
        lifecycleScope.launch {
            try {
                // Call the registration function from the repository
                userRepository.register(registerForm)

                val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                startActivity(intent)
                finish() // Optional: finish the current activity if you don't want the user to come back to it using the back button
            } catch (e: Exception) {
                // Handle registration failure (e.g., show an error message)
            }
        }
    }
}
