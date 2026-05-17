package com.example.contour.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.contour.R
import com.example.contour.data.DataStore
import com.example.contour.ui.flag.FlagReportActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TransactionDetailBottomSheet : BottomSheetDialogFragment() {

    override fun getTheme() = R.style.BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_transaction_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val transactionId = args?.getString("transaction_id", "") ?: ""

        view.findViewById<TextView>(R.id.txtDetailIcon).text = args?.getString("icon", "")
        view.findViewById<TextView>(R.id.txtDetailName).text = args?.getString("name", "")
        view.findViewById<TextView>(R.id.txtDetailAmount).text = args?.getString("amount", "")
        view.findViewById<TextView>(R.id.txtDetailCategory).text = args?.getString("category", "")

        view.findViewById<View>(R.id.btnClose).setOnClickListener { dismiss() }

        view.findViewById<View>(R.id.btnEdit).setOnClickListener {
            dismiss()
            startActivity(Intent(requireContext(), EditTransactionActivity::class.java).apply {
                putExtra("transaction_id", transactionId)
            })
        }

        view.findViewById<View>(R.id.btnDelete).setOnClickListener {
            if (transactionId.isNotEmpty()) {
                DataStore.deleteTransaction(requireContext(), transactionId)
                Toast.makeText(requireContext(), "Transaction deleted", Toast.LENGTH_SHORT).show()
            }
            dismiss()
        }

        view.findViewById<View>(R.id.btnSplit).setOnClickListener {
            dismiss()
            startActivity(Intent(requireContext(), FlagReportActivity::class.java))
        }
    }

    companion object {
        fun newInstance(icon: String, name: String, amount: String, category: String, transactionId: String = ""): TransactionDetailBottomSheet {
            return TransactionDetailBottomSheet().apply {
                arguments = Bundle().apply {
                    putString("icon", icon)
                    putString("name", name)
                    putString("amount", amount)
                    putString("category", category)
                    putString("transaction_id", transactionId)
                }
            }
        }
    }
}
