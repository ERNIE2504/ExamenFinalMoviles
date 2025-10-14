package com.app.countriesapp.data.repository

import com.app.countriesapp.data.mapper.toDomain
import com.app.countriesapp.data.remote.api.CountriesApiService
import com.app.countriesapp.domain.model.Country
import com.app.countriesapp.domain.repository.CountryRepository
import com.app.countriesapp.domain.model.Countries
import javax.inject.Inject


class CountriesRepositoryImpl
    @Inject
    constructor(
        private val api: CountriesApiService,
    ) : CountryRepository {
        override suspend fun getCountriesList(): List<Countries> {
            val response = api.getCountriesList()
            return response.map {it.toDomain()}
        }

        override suspend fun getCountryByName(nameCountry: String): Country {
            val response = api.getCountry(nameCountry)
            return response.first().toDomain()
        }
    }
