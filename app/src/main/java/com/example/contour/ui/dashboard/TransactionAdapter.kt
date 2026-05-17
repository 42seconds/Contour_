package com.example.contour.ui.dashboard

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contour.R

data class Transaction(
    val icon: String,
    val name: String,
    val date: String,
    val amount: String,
    val isIncome: Boolean = false
)

class TransactionAdapter(
    private val transactions: List<Transaction>,
    private val onClick: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtIcon: TextView = view.findViewById(R.id.txtIcon)
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtDate: TextView = view.findViewById(R.id.txtDate)
        val txtAmount: TextView = view.findViewById(R.id.txtAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tx = transactions[position]
        holder.txtIcon.text = tx.icon
        holder.txtName.text = tx.name
        holder.txtDate.text = tx.date
        holder.txtAmount.text = tx.amount
        holder.txtAmount.setTextColor(
            if (tx.isIncome) Color.parseColor("#10B981") else Color.parseColor("#000000")
        )
        holder.itemView.setOnClickListener { onClick(tx) }
    }

    override fun getItemCount() = transactions.size
}
