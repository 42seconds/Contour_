package com.example.contour.ui.reports

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.R
import com.example.contour.databinding.ActivityCategoryBreakdownBinding
import com.example.contour.ui.dashboard.DashboardActivity
import com.example.contour.ui.profile.ProfileSettingsActivity

class CategoryBreakdownActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBreakdownBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBreakdownBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        setupNavigation()
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
        findViewById<View>(R.id.navProfile).setOnClickListener {
            startActivity(Intent(this, ProfileSettingsActivity::class.java))
        }
    }
}
