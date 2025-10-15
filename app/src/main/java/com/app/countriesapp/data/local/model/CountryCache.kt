package com.app.countriesapp.data.local.model

data class CountryCache(
    val countryNames: List<String>,
    val lastUpdate: Long,
    val totalCount: Int
)