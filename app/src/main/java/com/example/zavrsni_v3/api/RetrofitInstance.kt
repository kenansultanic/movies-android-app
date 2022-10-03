package com.example.zavrsni_v3.api

import com.example.zavrsni_v3.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {

        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: MovieAPI by lazy {
        retrofit.create(MovieAPI::class.java)
    }
}