package com.example.interviewtask02

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.interviewtask02.adapter.UserAdapter
import com.example.interviewtask02.databinding.ActivityDbactivityBinding
import com.example.interviewtask02.db.UserDAO
import com.example.interviewtask02.db.UserDatabase
import com.example.interviewtask02.dialogue.AddDialogue
import com.example.interviewtask02.model.ModelUser

class DBActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDbactivityBinding
    private lateinit var userDAO: UserDAO

    private var userList: MutableList<ModelUser> = mutableListOf()
    private var tempUserList: MutableList<ModelUser> = mutableListOf()
    private lateinit var userAdapter: UserAdapter

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
        initSearch()

        binding.btnAdd.setOnClickListener { openInsertDialogue() }

        // get all user
        getALlUser()
    }

    private fun getALlUser() {
        userList = userDAO.getAllUser()

        userAdapter = UserAdapter(this, object : UserAdapter.OnClickListener {
            override fun onClick(user: ModelUser) {
                deleteUser(user)
            }
        })

        binding.rvUser.apply {
            setHasFixedSize(true)
            adapter = userAdapter
        }
        userAdapter.updateList(userList)
    }

    private fun deleteUser(user: ModelUser) {

        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Delete")
            setMessage("Do you want to delete this item?")
        }
        alertDialog.setPositiveButton("Yes") { _, i ->
            user.id?.let { deleteIndex ->
                userDAO.deleteUser(deleteIndex)

                if (deleteIndex > 0) {
                    Toast.makeText(this@DBActivity, "Delete Successfully", Toast.LENGTH_SHORT)
                        .show()
                    getALlUser()
                }
            }
        }
        alertDialog.setNegativeButton("No") { _, i -> }
        alertDialog.show()

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

    @SuppressLint("NotifyDataSetChanged")
    private fun initSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                tempUserList.clear()
                val searchText = p0!!.lowercase()
                if (searchText.isNotEmpty()) {
                    userList.forEach { user ->
                        user.name?.let { name ->
                            if (name.lowercase().contains(searchText)) {
                                tempUserList.add(user)
                            }
                        }
                    }
                } else {
                    tempUserList.clear()
                    tempUserList.addAll(userList)
                }

                userAdapter.updateList(tempUserList)
                userAdapter.notifyDataSetChanged()
                return false
            }

        })

        // search click listener
        binding.searchView.setOnQueryTextFocusChangeListener { _, hasFocused ->
            if (hasFocused) binding.txtName.visibility = View.GONE
            else binding.txtName.visibility = View.VISIBLE

        }
        binding.searchView.setOnCloseListener {
            binding.txtName.visibility = View.VISIBLE
            false
        }
    }
}