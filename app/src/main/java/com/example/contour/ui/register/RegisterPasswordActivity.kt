package com.example.contour.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import com.example.contour.R
import com.example.contour.databinding.ActivityRegisterPasswordBinding

class RegisterPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterPasswordBinding
    private var passwordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        binding.btnTogglePassword.setOnClickListener {
            passwordVisible = !passwordVisible
            if (passwordVisible) {
                binding.edtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.btnTogglePassword.setImageResource(R.drawable.ic_visibility)
            } else {
                binding.edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.btnTogglePassword.setImageResource(R.drawable.ic_visibility_off)
            }
            binding.edtPassword.setSelection(binding.edtPassword.text?.length ?: 0)
        }

        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val pwd = s.toString()
                updateRequirement(pwd.length >= 8, binding.iconReqLength, binding.txtReqLength)
                updateRequirement(pwd.any { it.isUpperCase() }, binding.iconReqUppercase, binding.txtReqUppercase)
                updateRequirement(pwd.any { !it.isLetterOrDigit() } || pwd.any { it.isDigit() }, binding.iconReqNumber, binding.txtReqNumber)
            }
        })

        binding.btnContinue.setOnClickListener {
            val pwd = binding.edtPassword.text.toString()
            if (pwd.length >= 8) {
                startActivity(Intent(this, RegisterCurrencyActivity::class.java))
            }
        }
    }

    private fun updateRequirement(met: Boolean, icon: android.widget.ImageView, text: android.widget.TextView) {
        if (met) {
            icon.setImageResource(R.drawable.ic_check_circle)
            text.setTextColor(getColor(R.color.text_secondary))
        } else {
            icon.setImageResource(R.drawable.ic_radio_unchecked)
            text.setTextColor(getColor(R.color.text_tertiary))
        }
    }
}
