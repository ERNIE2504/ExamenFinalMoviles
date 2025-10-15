package com.app.countriesapp.presentation.screens.countryDetail

import com.app.countriesapp.domain.model.Country

data class CountryDetailUiState(
    val country: Country? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
