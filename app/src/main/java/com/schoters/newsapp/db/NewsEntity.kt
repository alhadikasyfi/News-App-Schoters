package com.schoters.newsapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.schoters.newsapp.utils.Constants.NEWS_TABLE

@Entity(tableName = NEWS_TABLE)
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val newsId: Int,
    @ColumnInfo(name = "news_title")
    val newsTitle : String,
    @ColumnInfo(name = "news_description")
    val newsDescription : String,
    @ColumnInfo(name = "news_content")
    val newsContent : String,
    @ColumnInfo(name = "news_author")
    val newsAuthor : String
)
