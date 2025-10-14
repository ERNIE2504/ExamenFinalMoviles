package com.app.countriesapp.domain.model

data class Country(
    val name: String,
    val cca2: String,
    val region: String,
    val capital: List<String>,
    val population: Long,
    val flagUrl: String
)
