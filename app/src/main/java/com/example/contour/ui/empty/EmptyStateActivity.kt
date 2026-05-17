package com.example.contour.ui.empty

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.R
import com.example.contour.databinding.ActivityEmptyStateBinding
import com.example.contour.ui.dashboard.AddTransactionBottomSheet
import com.example.contour.ui.dashboard.DashboardActivity
import com.example.contour.ui.profile.ProfileSettingsActivity
import com.example.contour.ui.reports.SpendingTrendsActivity

class EmptyStateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmptyStateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmptyStateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        binding.btnAddTransaction.setOnClickListener {
            AddTransactionBottomSheet().show(supportFragmentManager, "add_transaction")
        }

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
