package com.st10083866.loginpageactivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var loginRedirectButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize views
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text)
        registerButton = findViewById(R.id.register_button)
        loginRedirectButton = findViewById(R.id.login_redirect_button)

        // Set click listeners
        registerButton.setOnClickListener {
            registerUser()
        }

        loginRedirectButton.setOnClickListener {
            // Navigate back to LoginActivity
            finish() // Just close this activity to go back to login
        }
    }

    private fun registerUser() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        if (email.isEmpty()) {
            emailEditText.error = "Email cannot be empty"
            return
        }

        if (password.isEmpty()) {
            passwordEditText.error = "Password cannot be empty"
            return
        }

        if (confirmPassword.isEmpty()) {
            confirmPasswordEditText.error = "Confirm password cannot be empty"
            return
        }

        if (password != confirmPassword) {
            confirmPasswordEditText.error = "Passwords don't match"
            return
        }

        // Create user with Firebase
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration success
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    // Return to login page
                    finish()
                } else {
                    // If registration fails, display a message to the user
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    }
