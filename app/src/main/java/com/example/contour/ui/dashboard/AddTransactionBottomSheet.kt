package com.example.contour.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.contour.R
import com.example.contour.data.DataStore
import com.example.contour.data.TransactionItem
import com.example.contour.ui.camera.UploadPhotoActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddTransactionBottomSheet : BottomSheetDialogFragment() {

    override fun getTheme() = R.style.BottomSheetDialog

    private var isIncome = false
    private var selectedCategory = "Food & Dining"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_add_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnExpense = view.findViewById<TextView>(R.id.btnExpense)
        val btnIncome = view.findViewById<TextView>(R.id.btnIncome)
        val btnSave = view.findViewById<View>(R.id.btnSave)
        val edtAmount = view.findViewById<EditText>(R.id.edtAmount)
        val edtDescription = view.findViewById<EditText>(R.id.edtDescription)
        val btnPhoto = view.findViewById<View>(R.id.btnPhoto)

        btnExpense.setOnClickListener {
            isIncome = false
            btnExpense.setBackgroundResource(R.drawable.bg_toggle_active)
            btnIncome.background = null
        }

        btnIncome.setOnClickListener {
            isIncome = true
            btnIncome.setBackgroundResource(R.drawable.bg_toggle_active)
            btnExpense.background = null
        }

        // Category chip selection
        val chipContainer = view.findViewById<LinearLayout>(R.id.chipCategoryContainer)
        val categories = listOf("Food & Dining", "Transport", "Shopping", "Housing", "Other")
        for (i in 0 until chipContainer.childCount) {
            val chip = chipContainer.getChildAt(i) as? TextView ?: continue
            chip.setOnClickListener {
                selectedCategory = if (i < categories.size) categories[i] else "Other"
                // Reset all chips
                for (j in 0 until chipContainer.childCount) {
                    val c = chipContainer.getChildAt(j) as? TextView ?: continue
                    c.setBackgroundResource(R.drawable.bg_chip_inactive)
                    c.setTextColor(resources.getColor(R.color.text_primary, null))
                }
                // Highlight selected
                chip.setBackgroundResource(R.drawable.bg_chip_active)
                chip.setTextColor(resources.getColor(R.color.white, null))
            }
        }

        btnPhoto.setOnClickListener {
            startActivity(Intent(requireContext(), UploadPhotoActivity::class.java))
        }

        btnSave.setOnClickListener {
            val amountStr = edtAmount.text.toString().trim()
            val description = edtDescription.text.toString().trim()

            if (amountStr.isEmpty() || description.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in amount and description", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountStr.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(requireContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dateFormat = SimpleDateFormat("MMM dd, hh:mm a", Locale.US)
            val category = if (isIncome) "Income" else selectedCategory

            val transaction = TransactionItem(
                description = description,
                amount = amount,
                category = category,
                isIncome = isIncome,
                date = dateFormat.format(Date())
            )

            DataStore.addTransaction(requireContext(), transaction)
            dismiss()
            startActivity(Intent(requireContext(), TransactionSuccessActivity::class.java).apply {
                putExtra("amount", amount)
                putExtra("description", description)
                putExtra("is_income", isIncome)
            })
        }
    }
}
