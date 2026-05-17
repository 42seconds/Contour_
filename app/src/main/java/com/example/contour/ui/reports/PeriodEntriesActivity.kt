package com.example.contour.ui.reports

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contour.R
import com.example.contour.data.DataStore
import com.example.contour.databinding.ActivityPeriodEntriesBinding
import com.example.contour.ui.dashboard.DashboardActivity
import com.example.contour.ui.dashboard.Transaction
import com.example.contour.ui.dashboard.TransactionAdapter
import com.example.contour.ui.profile.ProfileSettingsActivity
import com.example.contour.util.UserPreferences
import java.text.NumberFormat
import java.util.Locale

class PeriodEntriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPeriodEntriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeriodEntriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        setupNavigation()
    }

    override fun onResume() {
        super.onResume()
        setupTransactions()
    }

    private fun setupTransactions() {
        val symbol = UserPreferences.getCurrencySymbol(this)
        val allItems = DataStore.getTransactions(this)
        val nf = NumberFormat.getNumberInstance(Locale.US)
        nf.minimumFractionDigits = 2
        nf.maximumFractionDigits = 2

        val displayList = allItems.map { tx ->
            val prefix = if (tx.isIncome) "+" else "-"
            val icon = DashboardActivity.getCategoryIcon(tx.category)
            Transaction(icon, tx.description, "${tx.category} • ${tx.date}", "$prefix$symbol${nf.format(tx.amount)}", tx.isIncome)
        }

        binding.rvPeriodTransactions.layoutManager = LinearLayoutManager(this)
        binding.rvPeriodTransactions.adapter = TransactionAdapter(displayList) {}
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
