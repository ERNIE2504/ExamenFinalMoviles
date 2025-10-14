package com.app.countriesapp.presentation.screens.countryDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.countriesapp.domain.common.Result
import com.app.countriesapp.domain.usecase.GetCountryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CountryDetailViewModel @Inject constructor(
    private val getCountryUseCase: GetCountryUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(CountryDetailUiState())
    val uiState: StateFlow<CountryDetailUiState> = _uiState.asStateFlow()

    init {
        // si usas nav arg "countryName"
        val argName: String? = savedStateHandle["countryName"]
        if (!argName.isNullOrBlank()) {
            loadCountry(argName)
        }
    }

    fun loadCountry(nameCountry: String) {
        viewModelScope.launch {
            getCountryUseCase(nameCountry).collect { result ->
                when (result) {
                    is Result.Loading -> _uiState.update { it.copy(isLoading = true, error = null) }
                    is Result.Success -> _uiState.update { it.copy(country = result.data, isLoading = false, error = null) }
                    is Result.Error -> _uiState.update { it.copy(isLoading = false, error = result.exception.message) }
                }
            }
        }
    }
}

