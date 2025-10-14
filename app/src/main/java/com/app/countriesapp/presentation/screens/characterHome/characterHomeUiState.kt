package com.app.countriesapp.presentation.screens.characterHome

import com.app.countriesapp.domain.model.Country

@Suppress("ktlint:standard:class-naming")
data class characterHomeUiState(
    val countryList: List<Country> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
