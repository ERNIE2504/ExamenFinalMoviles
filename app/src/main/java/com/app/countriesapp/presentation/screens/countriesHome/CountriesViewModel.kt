package com.app.countriesapp.presentation.screens.countriesHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.countriesapp.data.local.preferences.CountryPreferences
import com.app.countriesapp.domain.common.Result
import com.app.countriesapp.domain.usecase.GetCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val prefs: CountryPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(CountriesHomeUiState())
    val uiState: StateFlow<CountriesHomeUiState> = _uiState.asStateFlow()

    init {
        loadCountriesList()
        restoreLastCountry()
    }

    private fun restoreLastCountry() {
        val last = prefs.getLastCountry()
        _uiState.update { it.copy(lastCountry = last) }
    }


    private fun loadCountriesList() {
        viewModelScope.launch {
            getCountriesUseCase().collect { result ->
                when (result) {
                    is Result.Loading -> _uiState.update { it.copy(isLoading = true, error = null) }
                    is Result.Success -> _uiState.update {
                        it.copy(countryList = result.data, isLoading = false, error = null)
                    }
                    is Result.Error -> _uiState.update {
                        it.copy(isLoading = false, error = result.exception.message)
                    }
                }
            }
        }
    }


    fun onCountryClicked(name: String) {
        viewModelScope.launch {
            prefs.setLastCountry(name)
            _uiState.update { it.copy(lastCountry = name) }
        }
    }


    fun clearLastCountry() {
        viewModelScope.launch {
            prefs.clearLastCountry()
            _uiState.update { it.copy(lastCountry = null) }
        }
    }
}