package com.example.contour.ui.goals

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.R
import com.example.contour.data.DataStore
import com.example.contour.databinding.ActivityGoalsListBinding
import com.example.contour.ui.dashboard.DashboardActivity
import com.example.contour.ui.profile.ProfileSettingsActivity
import com.example.contour.ui.reports.SpendingTrendsActivity
import com.example.contour.util.UserPreferences
import java.text.NumberFormat
import java.util.Locale

class GoalsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGoalsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        binding.btnAddGoal.setOnClickListener {
            AddGoalDialog().show(supportFragmentManager, "add_goal")
        }

        // Goal card clicks navigate to detail
        binding.goalEmergency.setOnClickListener {
            val goals = DataStore.getGoals(this)
            if (goals.isNotEmpty()) {
                startActivity(Intent(this, GoalDetailActivity::class.java).apply {
                    putExtra("goal_id", goals[0].id)
                })
            }
        }
        binding.goalVacation.setOnClickListener {
            val goals = DataStore.getGoals(this)
            if (goals.size > 1) {
                startActivity(Intent(this, GoalDetailActivity::class.java).apply {
                    putExtra("goal_id", goals[1].id)
                })
            }
        }
        binding.goalCar.setOnClickListener {
            val goals = DataStore.getGoals(this)
            if (goals.size > 2) {
                startActivity(Intent(this, GoalDetailActivity::class.java).apply {
                    putExtra("goal_id", goals[2].id)
                })
            }
        }

        setupNavigation()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val symbol = UserPreferences.getCurrencySymbol(this)
        val goals = DataStore.getGoals(this)
        val totalSaved = DataStore.getTotalSaved(this)

        // Update header
        // The XML has hardcoded TextViews — we'll find them by walking
        // For now update the progress bars based on goals data
        val goalViews = listOf(
            Triple(binding.goalEmergency, binding.progressEmergency, 0),
            Triple(binding.goalVacation, binding.progressVacation, 1),
            Triple(binding.goalCar, binding.progressCar, 2)
        )

        for ((container, progressView, index) in goalViews) {
            if (index < goals.size) {
                container.visibility = View.VISIBLE
                val goal = goals[index]
                val fraction = if (goal.targetAmount > 0) (goal.currentAmount / goal.targetAmount).toFloat().coerceAtMost(1f) else 0f
                setProgress(progressView, fraction)
            } else {
                container.visibility = View.GONE
            }
        }
    }

    private fun setProgress(view: View, fraction: Float) {
        view.post {
            val params = view.layoutParams
            params.width = ((view.parent as View).width * fraction).toInt()
            view.layoutParams = params
        }
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
        findViewById<View>(R.id.navBudget).setOnClickListener {
            startActivity(Intent(this, SpendingTrendsActivity::class.java))
            finish()
        }
        findViewById<View>(R.id.navProfile).setOnClickListener {
            startActivity(Intent(this, ProfileSettingsActivity::class.java))
        }
    }
}
