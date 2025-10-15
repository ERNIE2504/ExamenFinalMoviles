package com.app.countriesapp.data.mapper

import com.app.countriesapp.data.remote.dto.CountriesResponse
import com.app.countriesapp.domain.model.Countries

fun CountriesResponse.toDomain(): Countries =
    Countries(
        common = name.common,
    )