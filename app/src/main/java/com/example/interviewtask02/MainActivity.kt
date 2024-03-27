package com.example.interviewtask02

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.interviewtask02.adapter.QuoteAdapter
import com.example.interviewtask02.api.ApiClient
import com.example.interviewtask02.databinding.ActivityMainBinding
import com.example.interviewtask02.model.QuoteModel
import com.example.interviewtask02.utils.LoadingDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // loading
        loadingDialog = LoadingDialog(this)

        apiCall()

        binding.btnAdd.setOnClickListener { parseDBActivity() }


    }

    private fun apiCall() {
        val call = ApiClient.apiService.quoteApiCall()
        call.enqueue(object : Callback<QuoteModel> {
            override fun onResponse(call: Call<QuoteModel>, response: Response<QuoteModel>) {
                if (response.isSuccessful) {
                    if (loadingDialog.isShowing) loadingDialog.dismiss()
                    setData(response.body())

                } else {
                    // Handle error
                    if (loadingDialog.isShowing) loadingDialog.dismiss()
                    Toast.makeText(this@MainActivity, "Network Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<QuoteModel>, t: Throwable) {
                if (loadingDialog.isShowing) loadingDialog.dismiss()
                Toast.makeText(this@MainActivity, "Network Error", Toast.LENGTH_SHORT).show()

            }
        })
    }

    private fun setData(quote: QuoteModel?) {
        val quoteAdapter = QuoteAdapter(this, object : QuoteAdapter.OnClickListener {
            override fun onClick(quote: QuoteModel.QuoteItem) {
                Log.d("xxx", "Quote: $quote")
            }
        })

        binding.rvQuote.apply {
            setHasFixedSize(true)
            adapter = quoteAdapter
        }
        if (quote != null) {
            quoteAdapter.updateList(quote.quotes)
        }

    }

    private fun parseDBActivity() {
        startActivity(Intent(this, DBActivity::class.java))
    }
}
