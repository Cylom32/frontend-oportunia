package com.example.oportunia.presentation.ui.viewmodel

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oportunia.presentation.ui.Investigation.Language
import com.example.oportunia.presentation.ui.Investigation.LanguagePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val languagePreferences: LanguagePreferences
) : ViewModel() {

    private val _currentLanguage = MutableStateFlow(languagePreferences.getLanguage())
    val currentLanguage: StateFlow<Language> = _currentLanguage

    private val _shouldRecreate = MutableStateFlow(false)
    val shouldRecreate: StateFlow<Boolean> = _shouldRecreate

    fun changeLanguage(language: Language) {
        viewModelScope.launch {
            if (_currentLanguage.value != language) {
                languagePreferences.saveLanguage(language)
                _currentLanguage.value = language
                _shouldRecreate.value = true
            }
        }
    }

    fun onActivityRecreated() {
        _shouldRecreate.value = false
    }
}