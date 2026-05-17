package com.example.contour.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.databinding.ActivityProfileSetupBinding
import com.example.contour.ui.dashboard.DashboardActivity
import com.example.contour.util.UserPreferences

class ProfileSetupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileSetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        binding.btnSaveProfile.setOnClickListener {
            val username = binding.edtUsername.text.toString().trim()
            if (username.isNotEmpty()) {
                UserPreferences.saveUsername(this, username)
                UserPreferences.setLoggedIn(this, true)
                Toast.makeText(this, "Profile saved!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DashboardActivity::class.java))
                finishAffinity()
            }
        }
    }
}
