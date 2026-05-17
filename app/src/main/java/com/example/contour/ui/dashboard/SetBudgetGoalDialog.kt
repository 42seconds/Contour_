package com.example.contour.ui.dashboard

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.contour.R
import com.example.contour.data.BudgetItem
import com.example.contour.data.DataStore

class SetBudgetGoalDialog : DialogFragment() {

    private var selectedCategory = "Dining"
    private var selectedPeriod = "Monthly"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_set_budget_goal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.btnCloseGoal).setOnClickListener { dismiss() }

        val btnMonthly = view.findViewById<View>(R.id.btnMonthly)
        val btnCustom = view.findViewById<View>(R.id.btnCustom)

        btnMonthly.setOnClickListener {
            selectedPeriod = "Monthly"
            btnMonthly.setBackgroundResource(R.drawable.bg_toggle_active)
            btnCustom.background = null
        }

        btnCustom.setOnClickListener {
            selectedPeriod = "Custom"
            btnCustom.setBackgroundResource(R.drawable.bg_toggle_active)
            btnMonthly.background = null
        }

        // Category chip selection
        val categories = listOf("Dining", "Shopping", "Transport", "Housing", "Utilities", "Custom")
        val flexContainer = view.findViewById<ViewGroup>(R.id.flexCategories)
        for (i in 0 until flexContainer.childCount) {
            val chip = flexContainer.getChildAt(i) as? TextView ?: continue
            chip.setOnClickListener {
                selectedCategory = if (i < categories.size) categories[i] else "Other"
                for (j in 0 until flexContainer.childCount) {
                    val c = flexContainer.getChildAt(j) as? TextView ?: continue
                    c.setBackgroundResource(R.drawable.bg_chip_inactive)
                    c.setTextColor(resources.getColor(R.color.text_primary, null))
                }
                chip.setBackgroundResource(R.drawable.bg_chip_active)
                chip.setTextColor(resources.getColor(R.color.white, null))
            }
        }

        view.findViewById<View>(R.id.btnSetGoal).setOnClickListener {
            val amountStr = view.findViewById<EditText>(R.id.edtBudgetAmount).text.toString().trim()
            val amount = amountStr.toDoubleOrNull()

            if (amount == null || amount <= 0) {
                Toast.makeText(requireContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val icon = DashboardActivity.getCategoryIcon(selectedCategory)
            val budget = BudgetItem(
                category = selectedCategory,
                icon = icon,
                limit = amount,
                period = selectedPeriod
            )

            DataStore.addBudget(requireContext(), budget)
            Toast.makeText(requireContext(), "Budget goal set for $selectedCategory!", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}
