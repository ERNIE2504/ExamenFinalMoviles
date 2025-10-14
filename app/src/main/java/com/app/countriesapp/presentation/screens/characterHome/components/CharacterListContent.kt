package com.app.countriesapp.presentation.screens.characterHome.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.countriesapp.domain.model.Country
import com.app.countriesapp.presentation.common.components.LoadingShimmer

@Suppress("ktlint:standard:function-naming")
@Composable
fun CharacterListContent(
    countryList: List<Country>,
    isLoading: Boolean,
    error: String?,
    onCharacterClick: (String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(10) {
                        LoadingShimmer(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(160.dp),
                        )
                    }
                }
            }
            error != null -> {
                Text(
                    text = error,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error,
                )
            }
            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(
                        items = countryList,
                        key = { it.id },
                    ) { character ->
                        CharacterCard(
                            country = character,
                            onClick = { onCharacterClick(character.id) },
                        )
                    }
                }
            }
        }
    }
}
