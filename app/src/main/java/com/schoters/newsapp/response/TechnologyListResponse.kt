package com.schoters.newsapp.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class TechnologyListResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
) {
    @Keep
    data class Article(
        @SerializedName("author")
        val author: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("publishedAt")
        val publishedAt: String,
        @SerializedName("source")
        val source: Source,
        @SerializedName("title")
        val title: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("urlToImage")
        val urlToImage: String
    ) {
        @Keep
        data class Source(
            @SerializedName("id")
            val id: String,
            @SerializedName("name")
            val name: String
        )
    }
}