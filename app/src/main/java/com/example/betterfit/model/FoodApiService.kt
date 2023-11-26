package com.example.betterfit.model
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApiService {
    @GET("foods/search")
    fun searchFood(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("pageSize") pageSize: Int = 10
    ): Call<FoodSearchResponse>
}