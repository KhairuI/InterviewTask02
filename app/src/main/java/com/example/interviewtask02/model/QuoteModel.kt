package com.example.interviewtask02.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QuoteModel(
    @Expose @SerializedName("quotes") var quotes: MutableList<QuoteItem> = mutableListOf(),
    @Expose @SerializedName("total") var total: Int? = null,
    @Expose @SerializedName("skip") var skip: Int? = null,
    @Expose @SerializedName("limit") var limit: Int? = null
) {
    data class QuoteItem(
        @Expose @SerializedName("author") var author: String? = null,
        @Expose @SerializedName("id") var id: Int? = null,
        @Expose @SerializedName("quote") var quote: String? = null
    )
}