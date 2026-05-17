package com.example.contour.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.databinding.ActivityRegisterNameBinding
import com.example.contour.util.UserPreferences

class RegisterNameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set progress to 25%
        binding.progressFill.post {
            val parent = binding.progressFill.parent as ViewGroup
            val params = binding.progressFill.layoutParams
            params.width = (parent.width * 0.25).toInt()
            binding.progressFill.layoutParams = params
        }

        binding.btnBack.setOnClickListener { finish() }

        binding.btnContinue.setOnClickListener {
            val name = binding.edtFullName.text.toString().trim()
            if (name.isNotEmpty()) {
                UserPreferences.saveFullName(this, name)
                startActivity(Intent(this, RegisterEmailActivity::class.java))
            }
        }
    }
}
