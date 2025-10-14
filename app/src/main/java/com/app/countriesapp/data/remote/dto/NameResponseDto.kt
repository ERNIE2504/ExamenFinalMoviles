package com.app.countriesapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NameResponseDto(
    @SerializedName("common")
    val common: String,
)
