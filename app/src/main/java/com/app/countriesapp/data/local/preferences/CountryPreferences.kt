package com.app.countriesapp.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.app.countriesapp.data.local.model.CountryCache
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryPreferences @Inject constructor(
    @ApplicationContext context: Context,
    private val gson: Gson
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(CountryConstants.PREF_NAME, Context.MODE_PRIVATE)

    // Se guarda el último país
    fun setLastCountry(name: String) {
        prefs.edit { putString(CountryConstants.KEY_LAST_COUNTRY, name) }
    }

    fun getLastCountry(): String? =
        prefs.getString(CountryConstants.KEY_LAST_COUNTRY, null)

    fun clearLastCountry() {
        prefs.edit { remove(CountryConstants.KEY_LAST_COUNTRY) }
    }

    fun saveCountriesList(names: List<String>) {
        val json = gson.toJson(names)
        prefs.edit {
            putString(CountryConstants.KEY_COUNTRIES_CACHE, json)
            putLong(CountryConstants.KEY_LAST_UPDATE, System.currentTimeMillis())
            putInt(CountryConstants.KEY_TOTAL_COUNT, names.size)
        }
    }

    fun getCountriesCache(): CountryCache? {
        val json = prefs.getString(CountryConstants.KEY_COUNTRIES_CACHE, null) ?: return null
        val lastUpdate = prefs.getLong(CountryConstants.KEY_LAST_UPDATE, 0L)
        val totalCount = prefs.getInt(CountryConstants.KEY_TOTAL_COUNT, 0)

        val type = object : TypeToken<List<String>>() {}.type
        val names: List<String> = runCatching { gson.fromJson<List<String>>(json, type) }
            .getOrElse { emptyList() }

        return CountryCache(
            countryNames = names,
            lastUpdate = lastUpdate,
            totalCount = totalCount
        )
    }

    fun isCacheValid(): Boolean {
        val lastUpdate = prefs.getLong(CountryConstants.KEY_LAST_UPDATE, 0L)
        return System.currentTimeMillis() - lastUpdate < CountryConstants.CACHE_DURATION
    }

}
