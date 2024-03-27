package com.example.interviewtask02.api

import com.example.interviewtask02.model.QuoteModel
import com.example.interviewtask02.utils.AppConstants
import retrofit2.Call
import retrofit2.http.GET

interface ApiServiceQuote {

    @GET(AppConstants.ENDPOINT_QUOTES)
    fun quoteApiCall(): Call<QuoteModel>

}