package com.example.contour.ui.budget

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.contour.R
import com.example.contour.data.DataStore
import com.example.contour.ui.welcome.WelcomeActivity
import com.example.contour.util.UserPreferences

class SignOutDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_sign_out, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.btnConfirmSignOut).setOnClickListener {
            UserPreferences.clearAll(requireContext())
            DataStore.clearAll(requireContext())
            startActivity(Intent(requireContext(), WelcomeActivity::class.java))
            requireActivity().finishAffinity()
        }

        view.findViewById<View>(R.id.btnCancelSignOut).setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}
