package com.munyroth.majorrecommendation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.munyroth.majorrecommendation.model.enums.AppThemeEnum

class MainViewModelFactory(private val theme: AppThemeEnum, private val language: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(theme, language) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
