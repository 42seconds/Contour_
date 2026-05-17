package com.example.contour.ui.skeleton

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.databinding.ActivitySkeletonLoadingBinding

class SkeletonLoadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySkeletonLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySkeletonLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
