package com.example.contour.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.databinding.ActivityLoginBinding
import com.example.contour.ui.dashboard.DashboardActivity
import com.example.contour.ui.register.RegisterNameActivity
import com.example.contour.util.UserPreferences

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                UserPreferences.saveEmail(this, email)
                UserPreferences.setLoggedIn(this, true)
                startActivity(Intent(this, DashboardActivity::class.java))
                finishAffinity()
            }
        }

        binding.txtSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterNameActivity::class.java))
        }

        binding.txtForgotPassword.setOnClickListener {
            Toast.makeText(this, "Password reset feature coming soon", Toast.LENGTH_SHORT).show()
        }
    }
}
