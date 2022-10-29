package com.schoters.newsapp.api

import com.schoters.newsapp.response.TechnologyListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    // disini isi path dari url nya
    @GET("v2/top-headlines")
    fun getheadLinesTechnology(@Query("country") country : String, @Query("category") category : String) : Call<TechnologyListResponse>

    @GET("v2/top-headlines")
    fun getDetailNews(@Query("country") country : String, @Query("category") category : String, @Query("q") q : String) : Call<TechnologyListResponse>
}