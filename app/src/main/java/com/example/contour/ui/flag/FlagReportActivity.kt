package com.example.contour.ui.flag

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.R
import com.example.contour.databinding.ActivityFlagReportBinding

class FlagReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFlagReportBinding
    private var selectedReason = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlagReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        setupRadioCards()

        binding.btnSubmit.setOnClickListener {
            val reasons = listOf("Incorrect Amount", "Fraudulent", "Duplicate")
            Toast.makeText(this, "Report submitted: ${reasons[selectedReason]}", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupRadioCards() {
        binding.radioIncorrect.setOnClickListener { selectReason(0) }
        binding.radioFraudulent.setOnClickListener { selectReason(1) }
        binding.radioDuplicate.setOnClickListener { selectReason(2) }
    }

    private fun selectReason(index: Int) {
        selectedReason = index

        binding.radioIncorrect.setBackgroundResource(R.drawable.bg_radio_card)
        binding.radioFraudulent.setBackgroundResource(R.drawable.bg_radio_card)
        binding.radioDuplicate.setBackgroundResource(R.drawable.bg_radio_card)
        binding.radioIncorrectIcon.setImageResource(R.drawable.ic_radio_unchecked)
        binding.radioFraudulentIcon.setImageResource(R.drawable.ic_radio_unchecked)
        binding.radioDuplicateIcon.setImageResource(R.drawable.ic_radio_unchecked)

        when (index) {
            0 -> {
                binding.radioIncorrect.setBackgroundResource(R.drawable.bg_radio_card_selected)
                binding.radioIncorrectIcon.setImageResource(R.drawable.ic_radio_checked)
            }
            1 -> {
                binding.radioFraudulent.setBackgroundResource(R.drawable.bg_radio_card_selected)
                binding.radioFraudulentIcon.setImageResource(R.drawable.ic_radio_checked)
            }
            2 -> {
                binding.radioDuplicate.setBackgroundResource(R.drawable.bg_radio_card_selected)
                binding.radioDuplicateIcon.setImageResource(R.drawable.ic_radio_checked)
            }
        }
    }
}
