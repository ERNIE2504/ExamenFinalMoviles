package com.app.countriesapp.data.remote.api

import com.app.countriesapp.data.remote.dto.CountriesResponse
import com.app.countriesapp.data.remote.dto.CountryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApiService {
    @GET("all?fields=name")
    suspend fun getCountriesList(): List<CountriesResponse>

    @GET("name/{nameCountry}?fields=name,cca2,region,capital,population,flags")
    suspend fun getCountry(
        @Path("nameCountry") nameCountry: String,
    ): List<CountryResponse>
}
