package com.example.contour.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contour.databinding.ActivityRegisterCurrencyBinding
import com.example.contour.ui.profile.ProfileSetupActivity
import com.example.contour.util.UserPreferences

class RegisterCurrencyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterCurrencyBinding
    private lateinit var adapter: CurrencyAdapter

    private val allCurrencies = listOf(
        Currency("USD", "United States Dollar", "$"),
        Currency("EUR", "Euro", "€"),
        Currency("ZAR", "South African Rand", "R"),
        Currency("GBP", "British Pound", "£"),
        Currency("AUD", "Australian Dollar", "$"),
        Currency("CAD", "Canadian Dollar", "$")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterCurrencyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        adapter = CurrencyAdapter(allCurrencies) { currency ->
            UserPreferences.saveCurrency(this, currency.code, currency.symbol)
        }
        adapter.selectedCode = "ZAR"
        UserPreferences.saveCurrency(this, "ZAR", "R")
        binding.rvCurrencies.layoutManager = LinearLayoutManager(this)
        binding.rvCurrencies.adapter = adapter

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                adapter.filter(s.toString())
            }
        })

        binding.btnComplete.setOnClickListener {
            startActivity(Intent(this, ProfileSetupActivity::class.java))
        }
    }
}
