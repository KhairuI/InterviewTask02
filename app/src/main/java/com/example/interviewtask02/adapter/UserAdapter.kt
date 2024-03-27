package com.example.interviewtask02.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewtask02.databinding.ListItemUserBinding
import com.example.interviewtask02.model.ModelUser

class UserAdapter(
    private val context: Context,
    private val userList: List<ModelUser>,
    private val listener: OnClickListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ListItemUserBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (userList.isEmpty()) 0 else userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.binding.apply {

            tvName.text = user.name
            tvAddress.text = user.address
            user.id

            imgDelete.setOnClickListener {
                listener.onClick(user)
            }
        }
    }

    class UserViewHolder(val binding: ListItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnClickListener {
        fun onClick(user: ModelUser)
    }
}