package com.example.contour.ui.reports

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.databinding.ActivityReportsHubBinding
import com.example.contour.ui.goals.GoalsListActivity

class ReportsHubActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportsHubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportsHubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        binding.cardSpending.setOnClickListener {
            startActivity(Intent(this, SpendingTrendsActivity::class.java))
        }

        binding.cardCategory.setOnClickListener {
            startActivity(Intent(this, CategoryBreakdownActivity::class.java))
        }

        binding.cardIncome.setOnClickListener {
            startActivity(Intent(this, PeriodEntriesActivity::class.java))
        }

        binding.cardGoals.setOnClickListener {
            startActivity(Intent(this, GoalsListActivity::class.java))
        }
    }
}
