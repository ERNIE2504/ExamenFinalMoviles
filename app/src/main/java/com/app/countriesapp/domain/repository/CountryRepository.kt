@file:Suppress("ktlint:standard:filename")

package com.app.countriesapp.domain.repository

import com.app.countriesapp.domain.model.Countries
import com.app.countriesapp.domain.model.Country

interface CountryRepository {
    suspend fun getCountriesList(): List<Countries>

    suspend fun getCountryByName(nameCountry: String): Country
}
