package com.app.countriesapp.presentation.screens.countriesHome

import com.app.countriesapp.domain.model.Countries

@Suppress("ktlint:standard:class-naming")
data class CountriesHomeUiState(
    val countryList: List<Countries> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val lastCountry: String? = null
)
