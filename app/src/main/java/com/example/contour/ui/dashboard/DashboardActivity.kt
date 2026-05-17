package com.example.contour.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contour.data.DataStore
import com.example.contour.databinding.ActivityDashboardBinding
import com.example.contour.ui.profile.ProfileSettingsActivity
import com.example.contour.ui.reports.PeriodEntriesActivity
import com.example.contour.ui.reports.SpendingTrendsActivity
import com.example.contour.ui.search.SearchActivity
import com.example.contour.util.UserPreferences
import java.text.NumberFormat
import java.util.Locale

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }
        binding.btnSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.txtSeeAll.setOnClickListener {
            startActivity(Intent(this, PeriodEntriesActivity::class.java))
        }

        binding.fab.setOnClickListener {
            AddTransactionBottomSheet().show(supportFragmentManager, "add_transaction")
        }

        setupNavigation()
    }

    override fun onResume() {
        super.onResume()
        loadUserData()
        setupTransactions()
    }

    private fun loadUserData() {
        val firstName = UserPreferences.getFirstName(this)
        val symbol = UserPreferences.getCurrencySymbol(this)

        binding.txtGreeting.text = if (firstName.isNotEmpty()) "Hello, $firstName" else "Hello"

        val balance = DataStore.getBalance(this)
        val income = DataStore.getTotalIncome(this)
        val expenses = DataStore.getTotalExpenses(this)

        binding.txtBalance.text = formatCurrency(symbol, balance)
        binding.txtIncome.text = formatCurrency(symbol, income)
        binding.txtExpenses.text = formatCurrency(symbol, expenses)
    }

    private fun setupTransactions() {
        val items = DataStore.getTransactions(this)
        val symbol = UserPreferences.getCurrencySymbol(this)

        val displayList = items.take(5).map { tx ->
            val prefix = if (tx.isIncome) "+" else "-"
            val icon = getCategoryIcon(tx.category)
            Transaction(
                icon,
                tx.description,
                tx.date,
                "$prefix${formatCurrency(symbol, tx.amount)}",
                tx.isIncome
            )
        }

        binding.rvTransactions.layoutManager = LinearLayoutManager(this)
        binding.rvTransactions.adapter = TransactionAdapter(displayList) { tx ->
            // Find original transaction to get ID
            val original = items.find { it.description == tx.name }
            if (original != null) {
                TransactionDetailBottomSheet.newInstance(
                    tx.icon, tx.name, tx.amount, original.category, original.id
                ).show(supportFragmentManager, "transaction_detail")
            }
        }
    }

    private fun setupNavigation() {
        binding.navBudget.setOnClickListener {
            startActivity(Intent(this, SpendingTrendsActivity::class.java))
        }
        binding.navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileSettingsActivity::class.java))
        }
    }

    private fun formatCurrency(symbol: String, amount: Double): String {
        val nf = NumberFormat.getNumberInstance(Locale.US)
        nf.minimumFractionDigits = 2
        nf.maximumFractionDigits = 2
        return "$symbol${nf.format(amount)}"
    }

    companion object {
        fun getCategoryIcon(category: String): String {
            return when (category.lowercase()) {
                "food & dining", "food", "dining" -> "🍽️"
                "transport", "transportation" -> "🚗"
                "shopping" -> "🛍️"
                "housing", "rent" -> "🏠"
                "utilities", "bills" -> "⚡"
                "income", "salary" -> "💰"
                "fun", "entertainment" -> "🎉"
                else -> "💳"
            }
        }
    }
}
