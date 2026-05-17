package com.example.contour.ui.goals

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.data.DataStore
import com.example.contour.data.GoalItem
import com.example.contour.databinding.ActivityGoalDetailBinding
import com.example.contour.util.UserPreferences
import java.text.NumberFormat
import java.util.Locale

class GoalDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGoalDetailBinding
    private var goalId: String = ""
    private var currentGoal: GoalItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goalId = intent.getStringExtra("goal_id") ?: ""

        binding.btnBack.setOnClickListener { finish() }

        binding.btnEdit.setOnClickListener {
            // Add to current amount (simulate a deposit)
            currentGoal?.let { goal ->
                val deposit = goal.targetAmount * 0.1 // 10% deposit each time
                val updated = goal.copy(currentAmount = (goal.currentAmount + deposit).coerceAtMost(goal.targetAmount))
                DataStore.updateGoal(this, updated)
                Toast.makeText(this, "Added ${formatCurrency(UserPreferences.getCurrencySymbol(this), deposit)} to goal!", Toast.LENGTH_SHORT).show()
                loadGoal()
            }
        }

        binding.btnDelete.setOnClickListener {
            if (goalId.isNotEmpty()) {
                DataStore.deleteGoal(this, goalId)
                Toast.makeText(this, "Goal deleted", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        loadGoal()
    }

    private fun loadGoal() {
        if (goalId.isEmpty()) return

        currentGoal = DataStore.getGoals(this).find { it.id == goalId }
        currentGoal?.let { goal ->
            val symbol = UserPreferences.getCurrencySymbol(this)
            val remaining = (goal.targetAmount - goal.currentAmount).coerceAtLeast(0.0)

            binding.txtCurrentBalance.text = formatCurrency(symbol, goal.currentAmount)
            binding.txtTargetAmount.text = formatCurrency(symbol, goal.targetAmount)
            binding.txtRemaining.text = formatCurrency(symbol, remaining)
        }
    }

    private fun formatCurrency(symbol: String, amount: Double): String {
        val nf = NumberFormat.getNumberInstance(Locale.US)
        nf.minimumFractionDigits = 2
        nf.maximumFractionDigits = 2
        return "$symbol${nf.format(amount)}"
    }
}
