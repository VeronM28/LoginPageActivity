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

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerRedirectButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize views
        usernameEditText = findViewById(R.id.username_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        loginButton = findViewById(R.id.login_button)
        registerRedirectButton = findViewById(R.id.register_redirect_button)

        // Set click listeners
        loginButton.setOnClickListener {
            loginUser()
        }

        registerRedirectButton.setOnClickListener {
            // Navigate to RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser() {
        val email = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (email.isEmpty()) {
            usernameEditText.error = "Email cannot be empty"
            return
        }

        if (password.isEmpty()) {
            passwordEditText.error = "Password cannot be empty"
            return
        }

        // Sign in with Firebase
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, navigate to MainActivity
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Close this activity
                } else {
                    // If sign in fails, display a message to the user
                    Toast.makeText(this, "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in and update UI accordingly
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is already logged in, go directly to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    }
