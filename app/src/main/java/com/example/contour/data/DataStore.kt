package com.example.contour.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DataStore {

    private const val PREFS_NAME = "contour_data"
    private const val KEY_TRANSACTIONS = "transactions"
    private const val KEY_BUDGETS = "budgets"
    private const val KEY_GOALS = "goals"

    private val gson = Gson()

    private fun prefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // ─── Transactions ────────────────────────────────────────────

    fun getTransactions(context: Context): List<TransactionItem> {
        val json = prefs(context).getString(KEY_TRANSACTIONS, null) ?: return emptyList()
        val type = object : TypeToken<List<TransactionItem>>() {}.type
        return try { gson.fromJson(json, type) } catch (e: Exception) { emptyList() }
    }

    fun addTransaction(context: Context, item: TransactionItem) {
        val list = getTransactions(context).toMutableList()
        list.add(0, item) // newest first
        prefs(context).edit().putString(KEY_TRANSACTIONS, gson.toJson(list)).apply()
    }

    fun updateTransaction(context: Context, item: TransactionItem) {
        val list = getTransactions(context).toMutableList()
        val index = list.indexOfFirst { it.id == item.id }
        if (index >= 0) {
            list[index] = item
            prefs(context).edit().putString(KEY_TRANSACTIONS, gson.toJson(list)).apply()
        }
    }

    fun deleteTransaction(context: Context, id: String) {
        val list = getTransactions(context).toMutableList()
        list.removeAll { it.id == id }
        prefs(context).edit().putString(KEY_TRANSACTIONS, gson.toJson(list)).apply()
    }

    fun getTotalIncome(context: Context): Double {
        return getTransactions(context).filter { it.isIncome }.sumOf { it.amount }
    }

    fun getTotalExpenses(context: Context): Double {
        return getTransactions(context).filter { !it.isIncome }.sumOf { it.amount }
    }

    fun getBalance(context: Context): Double {
        return getTotalIncome(context) - getTotalExpenses(context)
    }

    // ─── Budgets ─────────────────────────────────────────────────

    fun getBudgets(context: Context): List<BudgetItem> {
        val json = prefs(context).getString(KEY_BUDGETS, null) ?: return emptyList()
        val type = object : TypeToken<List<BudgetItem>>() {}.type
        return try { gson.fromJson(json, type) } catch (e: Exception) { emptyList() }
    }

    fun addBudget(context: Context, item: BudgetItem) {
        val list = getBudgets(context).toMutableList()
        // Replace if same category exists
        list.removeAll { it.category == item.category }
        list.add(item)
        prefs(context).edit().putString(KEY_BUDGETS, gson.toJson(list)).apply()
    }

    fun deleteBudget(context: Context, id: String) {
        val list = getBudgets(context).toMutableList()
        list.removeAll { it.id == id }
        prefs(context).edit().putString(KEY_BUDGETS, gson.toJson(list)).apply()
    }

    fun getSpentForCategory(context: Context, category: String): Double {
        return getTransactions(context)
            .filter { !it.isIncome && it.category == category }
            .sumOf { it.amount }
    }

    fun getTotalBudgetLimit(context: Context): Double {
        return getBudgets(context).sumOf { it.limit }
    }

    fun getTotalSpent(context: Context): Double {
        return getTotalExpenses(context)
    }

    // ─── Goals ───────────────────────────────────────────────────

    fun getGoals(context: Context): List<GoalItem> {
        val json = prefs(context).getString(KEY_GOALS, null) ?: return emptyList()
        val type = object : TypeToken<List<GoalItem>>() {}.type
        return try { gson.fromJson(json, type) } catch (e: Exception) { emptyList() }
    }

    fun addGoal(context: Context, item: GoalItem) {
        val list = getGoals(context).toMutableList()
        list.add(item)
        prefs(context).edit().putString(KEY_GOALS, gson.toJson(list)).apply()
    }

    fun updateGoal(context: Context, item: GoalItem) {
        val list = getGoals(context).toMutableList()
        val index = list.indexOfFirst { it.id == item.id }
        if (index >= 0) {
            list[index] = item
            prefs(context).edit().putString(KEY_GOALS, gson.toJson(list)).apply()
        }
    }

    fun deleteGoal(context: Context, id: String) {
        val list = getGoals(context).toMutableList()
        list.removeAll { it.id == id }
        prefs(context).edit().putString(KEY_GOALS, gson.toJson(list)).apply()
    }

    fun getTotalSaved(context: Context): Double {
        return getGoals(context).sumOf { it.currentAmount }
    }

    // ─── Clear All ───────────────────────────────────────────────

    fun clearAll(context: Context) {
        prefs(context).edit().clear().apply()
    }
}
