package com.example.contour.ui.receipt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.databinding.ActivityReceiptViewerBinding

class ReceiptViewerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReceiptViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiptViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClose.setOnClickListener { finish() }
        binding.btnDone.setOnClickListener { finish() }
    }
}
