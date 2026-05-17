package com.example.contour.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.databinding.ActivityRegisterEmailBinding
import com.example.contour.util.UserPreferences

class RegisterEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        binding.btnContinue.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            if (email.isNotEmpty() && email.contains("@")) {
                UserPreferences.saveEmail(this, email)
                startActivity(Intent(this, RegisterPasswordActivity::class.java))
            }
        }
    }
}
