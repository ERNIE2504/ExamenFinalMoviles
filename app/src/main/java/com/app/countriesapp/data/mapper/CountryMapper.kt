package com.app.countriesapp.data.mapper

import com.app.countriesapp.data.remote.dto.CountryResponse
import com.app.countriesapp.domain.model.Country

fun CountryResponse.toDomain(): Country =
    Country(
        name = name.common,
        cca2 = cca2 ?: "",
        region = region ?: "",
        capital = capital ?: emptyList(),
        population = population ?: 0,
        flagUrl = flags?.png ?: ""
    )

