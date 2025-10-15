package com.app.countriesapp.presentation.screens.countryDetail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.countriesapp.domain.model.Country
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CountryDetailContent(country: Country) {
    val populationText = NumberFormat.getIntegerInstance(Locale.getDefault())
        .format(country.population)
    val capitalText = if (country.capital.isNotEmpty())
        country.capital.joinToString(", ")
    else "Sin capital"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = country.flagUrl,
            contentDescription = "Bandera de ${country.name}",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = country.name,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(12.dp))
        Divider()

        Spacer(Modifier.height(12.dp))
        InfoRow(label = "Capital", value = capitalText)
        InfoRow(label = "Región", value = country.region.ifEmpty { "Sin región" })
        InfoRow(label = "Código (CCA2)", value = country.cca2.ifEmpty { "Sin código" })
        InfoRow(label = "Población", value = populationText)
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}
