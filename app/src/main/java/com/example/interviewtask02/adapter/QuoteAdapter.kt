package com.example.interviewtask02.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewtask02.databinding.ListItemQuoteBinding
import com.example.interviewtask02.model.QuoteModel

class QuoteAdapter(
    private val context: Context,
    private val listener: OnClickListener
) : RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    private var quoteList: MutableList<QuoteModel.QuoteItem> = mutableListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): QuoteViewHolder {
        return QuoteViewHolder(
            ListItemQuoteBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (quoteList.isEmpty()) 0 else quoteList.size
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = quoteList[position]
        holder.binding.apply {

            tvID.text = quote.id.toString()
            tvQuoteText.text = quote.quote
            tvQuoteAuthor.text= quote.author


            cardBody.setOnClickListener {
                listener.onClick(quote)
            }
        }
    }

    class QuoteViewHolder(val binding: ListItemQuoteBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(quoteList: MutableList<QuoteModel.QuoteItem>) {
        this.quoteList = quoteList
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(quote: QuoteModel.QuoteItem)
    }
}