package com.example.headupp

import com.example.headupp.headsupCelebrity
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @GET("/celebrities/")
    fun getCelebrities(): Call<ArrayList<headsupCelebrity>>

    @POST("/celebrities/")
    fun addCelebrity(@Body celebrityData: headsupCelebrity): Call<headsupCelebrity>

    @GET("/celebrities/{id}")
    fun getCelebrity(@Path("id") id: Int): Call<headsupCelebrity>

    // PUT replaces the full object (use PATCH to change individual fields)
    @PUT("/celebrities/{id}")
    fun updateCelebrity(@Path("id") id: Int, @Body celebrityData: headsupCelebrity): Call<headsupCelebrity>

    @DELETE("/celebrities/{id}")
    fun deleteCelebrity(@Path("id") id: Int): Call<Void>
}
