package com.example.contour.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.databinding.ActivityTransactionSuccessBinding
import com.example.contour.util.UserPreferences
import java.text.NumberFormat
import java.util.Locale

class TransactionSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val amount = intent.getDoubleExtra("amount", 0.0)
        val description = intent.getStringExtra("description") ?: ""
        val isIncome = intent.getBooleanExtra("is_income", false)
        val symbol = UserPreferences.getCurrencySymbol(this)

        val nf = NumberFormat.getNumberInstance(Locale.US)
        nf.minimumFractionDigits = 2
        nf.maximumFractionDigits = 2
        val prefix = if (isIncome) "+" else "-"
        binding.txtAmount.text = "$prefix$symbol${nf.format(amount)}"

        binding.btnViewDashboard.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finishAffinity()
        }

        binding.btnAddAnother.setOnClickListener {
            finish()
        }
    }
}
