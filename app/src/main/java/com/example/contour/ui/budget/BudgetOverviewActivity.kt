package com.example.contour.ui.budget

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contour.R
import com.example.contour.data.DataStore
import com.example.contour.databinding.ActivityBudgetOverviewBinding
import com.example.contour.ui.dashboard.DashboardActivity
import com.example.contour.ui.profile.ProfileSettingsActivity
import com.example.contour.ui.reports.SpendingTrendsActivity
import com.example.contour.util.UserPreferences
import java.text.NumberFormat
import java.util.Locale

data class BudgetCategory(
    val icon: String,
    val name: String,
    val spent: String,
    val budget: String,
    val progress: Float,
    val isOverBudget: Boolean = false
)

class BudgetOverviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBudgetOverviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBudgetOverviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        setupNavigation()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val symbol = UserPreferences.getCurrencySymbol(this)
        val budgets = DataStore.getBudgets(this)
        val totalBudget = DataStore.getTotalBudgetLimit(this)
        val totalSpent = DataStore.getTotalSpent(this)
        val remaining = (totalBudget - totalSpent).coerceAtLeast(0.0)
        val usedPercent = if (totalBudget > 0) ((totalSpent / totalBudget) * 100).toInt().coerceAtMost(100) else 0

        binding.txtTotalBudget.text = formatCurrency(symbol, totalBudget)

        // Update spent and remaining text via finding views
        // These are in the XML but not bound — update progress
        binding.progressFill.post {
            val params = binding.progressFill.layoutParams
            val fraction = if (totalBudget > 0) (totalSpent / totalBudget).toFloat().coerceAtMost(1f) else 0f
            params.width = ((binding.progressFill.parent as View).width * fraction).toInt()
            binding.progressFill.layoutParams = params
        }

        val categories = budgets.map { budget ->
            val spent = DataStore.getSpentForCategory(this, budget.category)
            val progress = if (budget.limit > 0) (spent / budget.limit).toFloat().coerceAtMost(1f) else 0f
            BudgetCategory(
                icon = budget.icon,
                name = budget.category,
                spent = formatCurrency(symbol, spent),
                budget = "of ${formatCurrency(symbol, budget.limit)}",
                progress = progress,
                isOverBudget = spent > budget.limit
            )
        }

        binding.rvCategories.layoutManager = LinearLayoutManager(this)
        binding.rvCategories.adapter = BudgetCategoryAdapter(categories)
    }

    private fun formatCurrency(symbol: String, amount: Double): String {
        val nf = NumberFormat.getNumberInstance(Locale.US)
        nf.minimumFractionDigits = 2
        nf.maximumFractionDigits = 2
        return "$symbol${nf.format(amount)}"
    }

    private fun setupNavigation() {
        findViewById<View>(R.id.navTransactions).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
        findViewById<View>(R.id.navProfile).setOnClickListener {
            startActivity(Intent(this, ProfileSettingsActivity::class.java))
        }
    }
}

class BudgetCategoryAdapter(
    private val items: List<BudgetCategory>
) : RecyclerView.Adapter<BudgetCategoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtIcon: TextView = view.findViewById(R.id.txtCatIcon)
        val txtName: TextView = view.findViewById(R.id.txtCatName)
        val txtStatus: TextView = view.findViewById(R.id.txtCatStatus)
        val txtSpent: TextView = view.findViewById(R.id.txtCatSpent)
        val txtBudget: TextView = view.findViewById(R.id.txtCatBudget)
        val progressFill: View = view.findViewById(R.id.catProgressFill)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_budget_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.txtIcon.text = item.icon
        holder.txtName.text = item.name
        holder.txtStatus.text = if (item.isOverBudget) "Over budget" else "On track"
        holder.txtStatus.setTextColor(
            if (item.isOverBudget) Color.parseColor("#BA1A1A") else Color.parseColor("#6B7280")
        )
        holder.txtSpent.text = item.spent
        holder.txtBudget.text = item.budget

        holder.progressFill.post {
            val params = holder.progressFill.layoutParams
            params.width = ((holder.progressFill.parent as View).width * item.progress).toInt()
            holder.progressFill.layoutParams = params
        }

        if (item.isOverBudget) {
            holder.progressFill.setBackgroundResource(R.drawable.bg_progress_fill_error)
        }
    }

    override fun getItemCount() = items.size
}
