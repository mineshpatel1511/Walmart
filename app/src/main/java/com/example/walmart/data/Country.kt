package com.example.walmart.data

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val countryName: String,
    @SerializedName("region")
    val countryRegion: String,
    @SerializedName("code")
    val countryCode: String,
    @SerializedName("capital")
    var countryCapital: String,
)
