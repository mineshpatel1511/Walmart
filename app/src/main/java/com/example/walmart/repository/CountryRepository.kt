package com.example.walmart.repository

import com.example.walmart.data.Country
import com.example.walmart.network.RetrofitService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object CountryRepository {
    suspend fun getCountryList(): Flow<List<Country>> = flow {
        emit(RetrofitService.countryApi.getCountryList())
    }
}
