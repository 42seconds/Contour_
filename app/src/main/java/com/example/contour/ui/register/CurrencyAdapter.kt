package com.example.contour.ui.register

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contour.R

data class Currency(val code: String, val name: String, val symbol: String)

class CurrencyAdapter(
    private var currencies: List<Currency>,
    private val onSelect: (Currency) -> Unit
) : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    var selectedCode: String? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtSymbol: android.widget.TextView = view.findViewById(R.id.txtSymbol)
        val txtCode: android.widget.TextView = view.findViewById(R.id.txtCode)
        val txtName: android.widget.TextView = view.findViewById(R.id.txtName)
        val iconCheck: android.widget.ImageView = view.findViewById(R.id.iconCheck)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currency = currencies[position]
        holder.txtSymbol.text = currency.symbol
        holder.txtCode.text = currency.code
        holder.txtName.text = currency.name

        val isSelected = currency.code == selectedCode
        holder.iconCheck.visibility = if (isSelected) View.VISIBLE else View.GONE
        holder.itemView.setBackgroundColor(if (isSelected) Color.parseColor("#F9FAFB") else Color.TRANSPARENT)

        holder.itemView.setOnClickListener {
            selectedCode = currency.code
            notifyDataSetChanged()
            onSelect(currency)
        }
    }

    override fun getItemCount() = currencies.size

    fun filter(query: String) {
        currencies = if (query.isEmpty()) currencies
        else currencies.filter {
            it.code.contains(query, true) || it.name.contains(query, true)
        }
        notifyDataSetChanged()
    }
}
