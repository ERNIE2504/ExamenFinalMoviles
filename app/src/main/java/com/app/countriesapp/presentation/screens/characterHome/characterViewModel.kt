package com.app.countriesapp.presentation.screens.characterHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.countriesapp.domain.common.Result
import com.app.countriesapp.domain.usecase.GetCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("ktlint:standard:class-naming")
@HiltViewModel
class characterViewModel
    @Inject
    constructor(
        private val getCountriesUseCase: GetCountriesUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(characterHomeUiState())
        val uiState: StateFlow<characterHomeUiState> = _uiState.asStateFlow()

        init {
            loadCharacterList()
        }

        private fun loadCharacterList() {
            viewModelScope.launch {
                getCountriesUseCase().collect { result ->
                    _uiState.update { state ->
                        when (result) {
                            is Result.Loading ->
                                state.copy(
                                    isLoading = true,
                                )
                            is Result.Success ->
                                state.copy(
                                    countryList = result.data,
                                    isLoading = false,
                                    error = null,
                                )
                            is Result.Error ->
                                state.copy(
                                    error = result.exception.message,
                                    isLoading = false,
                                )
                        }
                    }
                }
            }
        }
    }
