package com.example.wallpaperapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {

    @GET("search")
    fun getdata(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Header("Authorization") auth:String
    ): Call<WallpaperModel>
}