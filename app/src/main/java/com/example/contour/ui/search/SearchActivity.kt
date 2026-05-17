package com.example.contour.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contour.data.DataStore
import com.example.contour.databinding.ActivitySearchBinding
import com.example.contour.ui.dashboard.DashboardActivity
import com.example.contour.ui.dashboard.Transaction
import com.example.contour.ui.dashboard.TransactionAdapter
import com.example.contour.util.UserPreferences
import java.text.NumberFormat
import java.util.Locale

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }
        binding.rvSearchResults.layoutManager = LinearLayoutManager(this)

        showResults("")

        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                showResults(s.toString())
            }
        })

        binding.editSearch.requestFocus()
    }

    private fun showResults(query: String) {
        val symbol = UserPreferences.getCurrencySymbol(this)
        val allItems = DataStore.getTransactions(this)

        val filtered = if (query.isBlank()) allItems else allItems.filter {
            it.description.contains(query, true) || it.category.contains(query, true)
        }

        val nf = NumberFormat.getNumberInstance(Locale.US)
        nf.minimumFractionDigits = 2
        nf.maximumFractionDigits = 2

        val displayList = filtered.map { tx ->
            val prefix = if (tx.isIncome) "+" else "-"
            val icon = DashboardActivity.getCategoryIcon(tx.category)
            Transaction(icon, tx.description, tx.date, "$prefix$symbol${nf.format(tx.amount)}", tx.isIncome)
        }

        binding.rvSearchResults.adapter = TransactionAdapter(displayList) {}
    }
}
