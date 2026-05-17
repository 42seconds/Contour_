package com.example.contour.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contour.R
import com.example.contour.databinding.ActivityWelcomeBinding
import com.example.contour.ui.register.RegisterNameActivity
import com.example.contour.ui.login.LoginActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(0, 0, 0, systemBars.bottom)
            insets
        }

        setupClickListeners()
        animateEntrance()
    }

    private fun setupClickListeners() {
        binding.btnGetStarted.setOnClickListener {
            startActivity(Intent(this, RegisterNameActivity::class.java))
        }

        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.txtLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun animateEntrance() {
        binding.txtTitle.alpha = 0f
        binding.txtTitle.translationY = 30f
        binding.txtTitle.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(800)
            .setStartDelay(300)
            .start()

        binding.btnGetStarted.alpha = 0f
        binding.btnGetStarted.translationY = 40f
        binding.btnGetStarted.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(600)
            .setStartDelay(600)
            .start()

        binding.btnSignIn.alpha = 0f
        binding.btnSignIn.translationY = 40f
        binding.btnSignIn.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(600)
            .setStartDelay(750)
            .start()

        binding.txtLogin.alpha = 0f
        binding.txtLogin.animate()
            .alpha(1f)
            .setDuration(500)
            .setStartDelay(1000)
            .start()
    }
}
