package com.example.contour.data

data class TransactionItem(
    val id: String = System.currentTimeMillis().toString(),
    val description: String,
    val amount: Double,
    val category: String,
    val isIncome: Boolean = false,
    val date: String,
    val note: String = ""
)

data class BudgetItem(
    val id: String = System.currentTimeMillis().toString(),
    val category: String,
    val icon: String,
    val limit: Double,
    val period: String = "Monthly"
)

data class GoalItem(
    val id: String = System.currentTimeMillis().toString(),
    val name: String,
    val icon: String,
    val targetAmount: Double,
    val currentAmount: Double = 0.0
)
