package com.app.countriesapp.domain.usecase

import com.app.countriesapp.domain.common.Result
import com.app.countriesapp.domain.model.Country
import com.app.countriesapp.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCountryUseCase
    @Inject
    constructor(
        private val repository: CountryRepository,
    ) {
        operator fun invoke(nameCountry: String): Flow<Result<Country>> =
            flow {
                try {
                    emit(Result.Loading)
                    val character = repository.getCountryByName(nameCountry)
                    emit(Result.Success(character))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
