package com.example.interviewtask02

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.interviewtask02.adapter.UserAdapter
import com.example.interviewtask02.databinding.ActivityDbactivityBinding
import com.example.interviewtask02.db.UserDAO
import com.example.interviewtask02.db.UserDatabase
import com.example.interviewtask02.dialogue.AddDialogue
import com.example.interviewtask02.dialogue.InsertDialogue
import com.example.interviewtask02.model.ModelUser

class DBActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDbactivityBinding
    private lateinit var userDAO: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDbactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // init DB
        userDAO = UserDatabase.getInstance(this).userDao


        binding.btnAdd.setOnClickListener { openInsertDialogue() }

        // get all user
        getALlUser()


    }

    private fun getALlUser() {
        val users = userDAO.getAllUser()

        val userAdapter = UserAdapter(this, users, object : UserAdapter.OnClickListener {
            override fun onClick(user: ModelUser) {
                val index = user.id?.let { deleteIndex ->
                    userDAO.deleteUser(deleteIndex)

                    if (deleteIndex > 0) {
                        Toast.makeText(this@DBActivity, "Delete Successfully", Toast.LENGTH_SHORT)
                            .show()
                        getALlUser()
                    }
                }
            }

        })

        binding.rvUser.apply {
            setHasFixedSize(true)
            adapter = userAdapter
        }
    }

    private fun openInsertDialogue() {
        val dialogue = AddDialogue(this, object : AddDialogue.OnClickListener {
            override fun onClick(user: ModelUser) {
                val index = userDAO.insert(user)
                if (index > 0) {
                    Toast.makeText(this@DBActivity, "Added Successfully", Toast.LENGTH_SHORT).show()
                    getALlUser()
                }
            }

        })

        dialogue.show()
    }
}