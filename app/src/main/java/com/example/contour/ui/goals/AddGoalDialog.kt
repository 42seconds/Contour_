package com.example.contour.ui.goals

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.contour.R
import com.example.contour.data.DataStore
import com.example.contour.data.GoalItem

class AddGoalDialog : DialogFragment() {

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
        return inflater.inflate(R.layout.dialog_add_goal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val edtName = view.findViewById<EditText>(R.id.edtGoalName)
        val edtTarget = view.findViewById<EditText>(R.id.edtGoalTarget)
        val btnSave = view.findViewById<View>(R.id.btnSaveGoal)
        val btnClose = view.findViewById<View>(R.id.btnCloseGoal)

        btnClose.setOnClickListener { dismiss() }

        btnSave.setOnClickListener {
            val name = edtName.text.toString().trim()
            val targetStr = edtTarget.text.toString().trim()
            val target = targetStr.toDoubleOrNull()

            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter a goal name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (target == null || target <= 0) {
                Toast.makeText(requireContext(), "Please enter a valid target amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val icon = when {
                name.contains("emergency", true) -> "🛡️"
                name.contains("vacation", true) || name.contains("travel", true) -> "✈️"
                name.contains("car", true) -> "🚗"
                name.contains("house", true) || name.contains("home", true) -> "🏠"
                name.contains("education", true) || name.contains("school", true) -> "📚"
                else -> "🎯"
            }

            val goal = GoalItem(
                name = name,
                icon = icon,
                targetAmount = target
            )
            DataStore.addGoal(requireContext(), goal)
            Toast.makeText(requireContext(), "Goal created!", Toast.LENGTH_SHORT).show()
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
