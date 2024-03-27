package com.example.interviewtask02.api

import com.example.interviewtask02.utils.AppConstants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object ApiClient {
    val apiService: ApiServiceQuote by lazy {
        RetrofitClient.retrofit.create(ApiServiceQuote::class.java)
    }
}