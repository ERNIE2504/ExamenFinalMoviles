package com.app.countriesapp.presentation.screens.countriesHome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.countriesapp.presentation.screens.countriesHome.components.CountriesListContent

@Suppress("ktlint:standard:function-naming")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountriesHomeScreen(
    onCountryClick: (String) -> Unit,
    viewModel: CountriesViewModel = hiltViewModel(),
) {

    var searchQuery by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Esta función se hizo con ayuda de ChatGpt para que me ayudará a buscar el último país
    // y muestra el detalle con rememberSevaeable guarda el valor de la varible aunque esta vaya cambiando
    // gracias también a LaunchedEffect que solo actúa cuando es necesario
    // el didAutoNavigate ayuda a que se auto navege si existe el lastCountry si no no hace nada
    val didAutoNavigate = rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(uiState.lastCountry, didAutoNavigate.value) {
        val last = uiState.lastCountry
        if (!didAutoNavigate.value && !last.isNullOrBlank()) {
            didAutoNavigate.value = true
            onCountryClick(last)
        }
    }



    var showDesignInfo by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Countries") },
            )
        },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Seach country...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                singleLine = true,
            )
            androidx.compose.material3.FilledTonalButton(
                onClick = { showDesignInfo = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Ver info del diseño")
            }

            Spacer(Modifier.height(12.dp))
            // Filtrar paises según la búsqueda
            val filteredCharacters =
                uiState.countryList.filter { character ->
                    character.common.contains(searchQuery, ignoreCase = true)
                }

                    CountriesListContent(
                        countryList = filteredCharacters,
                        isLoading = uiState.isLoading,
                        error = uiState.error,
                        onCountryClick = { name ->
                            viewModel.onCountryClicked(name)
                            onCountryClick(name)
                        }
                    )
            }
        }

    if (showDesignInfo) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDesignInfo = false },
            confirmButton = {
                androidx.compose.material3.TextButton(onClick = { showDesignInfo = false }) {
                    Text("Entendido")
                }
            },
            title = { Text("Self-explained hook") },
            text = {
                Column {
                    Text("• Arquitectura elegida: MVVM + Clean. Carpetas: data, di, domain, presentation. La interfaz real: CountryRepository, con funciones como getCountriesList() y getCountryByName(name). Los casos de uso (GetCountriesUseCase y GetCountryUseCase)")
                    Spacer(Modifier.height(8.dp))
                    Text("• Estrategia de guardado de preferencias: wrapper CountryPreferences (SharedPreferences + Gson). Guarda KEY_LAST_COUNTRY para recordar el último país abierto y cachea la lista de nombres con KEY_COUNTRIES_CACHE + KEY_LAST_UPDATE")
                    Spacer(Modifier.height(8.dp))
                    Text("• Estrategia de búsqueda: en Home se filtra por nombre (common) usando la memoria guardada sobre la lista ya cargada")
                }
            }
        )
    }

}
