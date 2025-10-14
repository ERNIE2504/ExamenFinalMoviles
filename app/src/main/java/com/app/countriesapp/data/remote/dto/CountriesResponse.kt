package com.app.countriesapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CountriesResponse(
    @SerializedName("name")
    val name: NameResponseDto,
)
