package com.app.countriesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.app.countriesapp.presentation.navigation.CountryNavGraph
import com.app.countriesapp.presentation.theme.CountryAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CountryAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CountryNavGraph()
                }
            }
        }
    }
}
