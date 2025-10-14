package com.app.countriesapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("name")
    val name: NameResponseDto,

    @SerializedName("cca2")
    val cca2: String?,

    @SerializedName("region")
    val region: String?,

    @SerializedName("capital")
    val capital: List<String>?,

    @SerializedName("population")
    val population: Long?,

    @SerializedName("flags")
    val flags: FlagsResponseDto?
)
