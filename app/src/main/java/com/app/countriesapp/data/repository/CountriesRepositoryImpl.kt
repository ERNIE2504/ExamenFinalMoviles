package com.app.countriesapp.data.repository

import com.app.countriesapp.data.local.preferences.CountryPreferences
import com.app.countriesapp.data.mapper.toDomain
import com.app.countriesapp.data.remote.api.CountriesApiService
import com.app.countriesapp.domain.model.Country
import com.app.countriesapp.domain.repository.CountryRepository
import com.app.countriesapp.domain.model.Countries
import javax.inject.Inject
class CountriesRepositoryImpl @Inject constructor(
    private val api: CountriesApiService,
    private val preferences: CountryPreferences
) : CountryRepository {

    override suspend fun getCountriesList(): List<Countries> {
        preferences.getCountriesCache()?.let { cache ->
            if (preferences.isCacheValid() && cache.countryNames.isNotEmpty()) {
                return cache.countryNames
                    .map { name -> Countries(common = name) }
                    .sortedBy { it.common }
            }
        }

        return try {
            val remote = api.getCountriesList()
            val domain = remote.map { it.toDomain() }
                .sortedBy { it.common }
            preferences.saveCountriesList(domain.map { it.common })

            domain
        } catch (e: Exception) {
            preferences.getCountriesCache()?.let { cache ->
                if (cache.countryNames.isNotEmpty()) {
                    return cache.countryNames
                        .map { Countries(common = it) }
                        .sortedBy { it.common }
                }
            }
            throw e
        }
    }

    override suspend fun getCountryByName(nameCountry: String): Country {
        val response = api.getCountry(nameCountry)
        return response.first().toDomain() }
    }

