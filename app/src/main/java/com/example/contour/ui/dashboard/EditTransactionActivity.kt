package com.example.contour.ui.dashboard

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.data.DataStore
import com.example.contour.data.TransactionItem
import com.example.contour.databinding.ActivityEditTransactionBinding

class EditTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTransactionBinding
    private var transactionId: String = ""
    private var currentTransaction: TransactionItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactionId = intent.getStringExtra("transaction_id") ?: ""
        loadTransaction()

        binding.btnBack.setOnClickListener { finish() }

        binding.btnSave.setOnClickListener {
            saveTransaction()
        }

        binding.btnDelete.setOnClickListener {
            if (transactionId.isNotEmpty()) {
                DataStore.deleteTransaction(this, transactionId)
                Toast.makeText(this, "Transaction deleted", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }

    private fun loadTransaction() {
        if (transactionId.isEmpty()) return

        currentTransaction = DataStore.getTransactions(this).find { it.id == transactionId }
        currentTransaction?.let { tx ->
            binding.editAmount.setText(String.format("%.2f", tx.amount))
            binding.editMerchant.setText(tx.description)
            binding.editNote.setText(tx.note)
            binding.editDate.text = tx.date
        }
    }

    private fun saveTransaction() {
        val amountStr = binding.editAmount.text.toString().trim()
        val merchant = binding.editMerchant.text.toString().trim()
        val note = binding.editNote.text.toString().trim()

        if (amountStr.isEmpty() || merchant.isEmpty()) {
            Toast.makeText(this, "Please fill in amount and merchant", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            return
        }

        if (transactionId.isNotEmpty() && currentTransaction != null) {
            val updated = currentTransaction!!.copy(
                description = merchant,
                amount = amount,
                note = note
            )
            DataStore.updateTransaction(this, updated)
            Toast.makeText(this, "Transaction updated", Toast.LENGTH_SHORT).show()
        } else {
            // Creating new from edit screen (unlikely but handle it)
            val newTx = TransactionItem(
                description = merchant,
                amount = amount,
                category = "Other",
                date = binding.editDate.text.toString(),
                note = note
            )
            DataStore.addTransaction(this, newTx)
            Toast.makeText(this, "Transaction saved", Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}
