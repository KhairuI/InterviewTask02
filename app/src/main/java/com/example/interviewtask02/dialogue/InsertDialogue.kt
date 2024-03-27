package com.example.interviewtask02.dialogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.interviewtask02.R
import com.example.interviewtask02.model.ModelUser

class InsertDialogue(private val listener: OnClickListener) : DialogFragment() {

    //https://guides.codepath.com/android/Using-DialogFragment (tutorial)

    // private lateinit var binding: InsertDialogueBinding

    private lateinit var edtName: EditText
    private lateinit var edtAddress: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edtName = view.findViewById(R.id.edtName)
        edtAddress = view.findViewById(R.id.edtAddress)

        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.insert_dialogue, container)
    }


    private fun getName(): String = edtName.text.toString()

    private fun getAddress(): String = edtAddress.text.toString()

    private fun isValidate(): Boolean {
        if (getName().isEmpty()) {
            edtName.error = "Name is missing"
            return false
        }
        if (getAddress().isEmpty()) {
            edtAddress.error = "Address is missing"
            return false
        }

        return true
    }

    interface OnClickListener {
        fun onClick(user: ModelUser)
    }
}