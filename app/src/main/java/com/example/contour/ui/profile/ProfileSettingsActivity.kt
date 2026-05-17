package com.example.contour.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.R
import com.example.contour.databinding.ActivityProfileSettingsBinding
import com.example.contour.ui.budget.SignOutDialog
import com.example.contour.ui.dashboard.DashboardActivity
import com.example.contour.ui.notifications.NotificationsActivity
import com.example.contour.ui.reports.SpendingTrendsActivity
import com.example.contour.util.UserPreferences

class ProfileSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        loadUserData()

        binding.menuNotifications.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        binding.menuSecurity.setOnClickListener {
            Toast.makeText(this, "Security settings coming soon", Toast.LENGTH_SHORT).show()
        }

        binding.menuHelp.setOnClickListener {
            Toast.makeText(this, "Help & Support coming soon", Toast.LENGTH_SHORT).show()
        }

        binding.btnLogout.setOnClickListener {
            SignOutDialog().show(supportFragmentManager, "sign_out")
        }

        setupNavigation()
    }

    override fun onResume() {
        super.onResume()
        loadUserData()
    }

    private fun loadUserData() {
        val fullName = UserPreferences.getFullName(this)
        val email = UserPreferences.getEmail(this)
        val currencyCode = UserPreferences.getCurrencyCode(this)
        val currencySymbol = UserPreferences.getCurrencySymbol(this)

        binding.txtProfileName.text = if (fullName.isNotEmpty()) fullName else "Set up your profile"
        binding.txtProfileEmail.text = if (email.isNotEmpty()) email else "No email set"
        binding.txtCurrencyDisplay.text = "$currencyCode ($currencySymbol) ›"
    }

    private fun setupNavigation() {
        findViewById<View>(R.id.navTransactions).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
        findViewById<View>(R.id.navBudget).setOnClickListener {
            startActivity(Intent(this, SpendingTrendsActivity::class.java))
            finish()
        }

        // Highlight Profile as active tab
        findViewById<View>(R.id.navProfileIcon)?.let {
            (it as? android.widget.ImageView)?.setColorFilter(android.graphics.Color.parseColor("#10B981"))
        }
        findViewById<android.widget.TextView>(R.id.navProfileLabel)?.let {
            it.setTextColor(android.graphics.Color.parseColor("#10B981"))
            it.setTypeface(it.typeface, android.graphics.Typeface.BOLD)
        }
    }
}
