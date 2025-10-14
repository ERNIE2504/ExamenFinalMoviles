package com.app.countriesapp.domain.usecase

import com.app.countriesapp.domain.common.Result
import com.app.countriesapp.domain.model.Countries
import com.app.countriesapp.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCountriesUseCase
    @Inject
    constructor(
        private val repository: CountryRepository,
    ) {
        operator fun invoke(): Flow<Result<List<Countries>>> =
            flow {
                try {
                    emit(Result.Loading)
                    val countriesList = repository.getCountriesList()
                    emit(Result.Success(countriesList))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
