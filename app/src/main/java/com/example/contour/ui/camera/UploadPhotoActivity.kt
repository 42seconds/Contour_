package com.example.contour.ui.camera

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.databinding.ActivityUploadPhotoBinding

class UploadPhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClose.setOnClickListener { finish() }

        binding.btnCapture.setOnClickListener {
            // Capture photo action
        }

        binding.btnGallery.setOnClickListener {
            // Open gallery action
        }
    }
}
