package com.example.walmart.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val COUNTRY_LIST_URL = "https://gist.githubusercontent.com/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(COUNTRY_LIST_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val countryApi: CountryApi = getRetrofit().create(CountryApi::class.java)
}
