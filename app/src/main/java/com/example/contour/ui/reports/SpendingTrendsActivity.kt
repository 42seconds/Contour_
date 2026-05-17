package com.example.contour.ui.reports

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.R
import com.example.contour.data.DataStore
import com.example.contour.databinding.ActivitySpendingTrendsBinding
import com.example.contour.ui.budget.BudgetOverviewActivity
import com.example.contour.ui.dashboard.DashboardActivity
import com.example.contour.ui.goals.GoalsListActivity
import com.example.contour.ui.profile.ProfileSettingsActivity

class SpendingTrendsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpendingTrendsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpendingTrendsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        setupNavigation()
    }

    private fun setupNavigation() {
        findViewById<View>(R.id.navTransactions).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
        findViewById<View>(R.id.navProfile).setOnClickListener {
            startActivity(Intent(this, ProfileSettingsActivity::class.java))
        }

        // Highlight Budget as active tab
        findViewById<View>(R.id.navBudgetIcon)?.let {
            (it as? android.widget.ImageView)?.setColorFilter(android.graphics.Color.parseColor("#10B981"))
        }
        findViewById<android.widget.TextView>(R.id.navBudgetLabel)?.let {
            it.setTextColor(android.graphics.Color.parseColor("#10B981"))
            it.setTypeface(it.typeface, android.graphics.Typeface.BOLD)
        }
    }
}
