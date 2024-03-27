package com.example.interviewtask02.dialogue

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.interviewtask02.databinding.InsertDialogueBinding
import com.example.interviewtask02.model.ModelUser

class AddDialogue(
    private val context: Context,
    private val listener: OnClickListener
) : AlertDialog(context) {

    private val binding = InsertDialogueBinding.inflate(LayoutInflater.from(context), null, false).apply {

        btnCancel.setOnClickListener { dismiss() }

        btnAdd.setOnClickListener {
            if (isValidate()) {
                listener.onClick((ModelUser(getName(), getAddress())))
                dismiss()
            }
        }
    }

    init {
        setCancelable(false)
        window?.apply {
            setDimAmount(0f)
            //setBackgroundDrawableResource(android.R.color.transparent)

        }
        setView(binding.root)
    }

    private fun getName(): String = binding.edtName.text.toString()

    private fun getAddress(): String = binding.edtAddress.text.toString()

    private fun isValidate(): Boolean {
        if (getName().isEmpty()) {
            binding.edtName.error = "Name is missing"
            return false
        }
        if (getAddress().isEmpty()) {
            binding.edtAddress.error = "Address is missing"
            return false
        }

        return true
    }

    interface OnClickListener {
        fun onClick(user: ModelUser)
    }
}