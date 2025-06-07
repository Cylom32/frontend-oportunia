package com.example.oportunia.presentation.ui.Investigation

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguagePreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences("language_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val LANGUAGE_KEY = "selected_language"
    }

    fun saveLanguage(language: Language) {
        prefs.edit().putString(LANGUAGE_KEY, language.code).apply()
    }

    fun getLanguage(): Language {
        val savedLanguage = prefs.getString(LANGUAGE_KEY, null)
        return Language.values().find { it.code == savedLanguage } ?: Language.SPANISH
    }



    fun Context.setAppLanguage(languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)

        return createConfigurationContext(configuration)
    }

    fun Activity.updateLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)

        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    fun Activity.recreateWithLanguage(language: Language) {
        updateLanguage(language.code)
        recreate()
    }
}
