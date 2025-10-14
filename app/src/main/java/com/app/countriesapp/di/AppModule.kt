package com.app.countriesapp.di

import com.app.countriesapp.data.remote.api.CountriesApiService
import com.app.countriesapp.data.repository.CountriesRepositoryImpl
import com.app.countriesapp.domain.repository.CountryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// di/AppModule.kt
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideCountryApiService(retrofit: Retrofit): CountriesApiService = retrofit.create(CountriesApiService::class.java)

    @Provides
    @Singleton
    fun provideCountryRepository(api: CountriesApiService): CountryRepository = CountriesRepositoryImpl(api)
}
