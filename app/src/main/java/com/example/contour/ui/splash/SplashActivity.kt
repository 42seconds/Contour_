package com.example.contour.ui.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.databinding.ActivitySplashBinding
import com.example.contour.ui.dashboard.DashboardActivity
import com.example.contour.ui.welcome.WelcomeActivity
import com.example.contour.util.UserPreferences

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animateLogo()

        Handler(Looper.getMainLooper()).postDelayed({
            val destination = if (UserPreferences.isLoggedIn(this)) {
                DashboardActivity::class.java
            } else {
                WelcomeActivity::class.java
            }
            startActivity(Intent(this, destination))
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, 2200)
    }

    private fun animateLogo() {
        binding.imgLogo.scaleX = 0f
        binding.imgLogo.scaleY = 0f
        binding.imgLogo.alpha = 0f

        val scaleX = ObjectAnimator.ofFloat(binding.imgLogo, "scaleX", 0f, 1f).apply {
            duration = 800
            interpolator = OvershootInterpolator(1.5f)
        }
        val scaleY = ObjectAnimator.ofFloat(binding.imgLogo, "scaleY", 0f, 1f).apply {
            duration = 800
            interpolator = OvershootInterpolator(1.5f)
        }
        val alpha = ObjectAnimator.ofFloat(binding.imgLogo, "alpha", 0f, 1f).apply {
            duration = 600
        }

        AnimatorSet().apply {
            playTogether(scaleX, scaleY, alpha)
            startDelay = 200
            start()
        }
    }
}
