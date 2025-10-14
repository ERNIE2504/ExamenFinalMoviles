package com.app.countriesapp.presentation.screens.characterHome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.countriesapp.presentation.screens.characterHome.components.CharacterListContent

@Suppress("ktlint:standard:function-naming")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterHomeScreen(
    onCharacterClick: (String) -> Unit,
    viewModel: characterViewModel = hiltViewModel(),
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Filtrar personajes según la búsqueda
    val filteredCharacters =
        uiState.countryList.filter { character ->
            character.name.contains(searchQuery, ignoreCase = true)
        }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Characters") },
            )
        },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Seach character...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                singleLine = true,
            )

            // Character List
            when (selectedTabIndex) {
                0 ->
                    CharacterListContent(
                        countryList = filteredCharacters,
                        isLoading = uiState.isLoading,
                        error = uiState.error,
                        onCharacterClick = onCharacterClick,
                    )
            }
        }
    }
}
