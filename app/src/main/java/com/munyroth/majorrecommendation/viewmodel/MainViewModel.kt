package com.munyroth.majorrecommendation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.munyroth.majorrecommendation.api.RetrofitInstance
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.ResData
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.model.Token
import com.munyroth.majorrecommendation.model.enums.AppThemeEnum

class MainViewModel : BaseViewModel {
    var stateApp: MainState? by mutableStateOf(null)

    constructor(theme: AppThemeEnum, language: String) {
        stateApp = MainState(theme, language)
    }

    constructor()

    private val _fcmToken = mutableStateOf(ApiData<ResData<String>>(Status.LOADING, null))
    val fcmToken: MutableState<ApiData<ResData<String>>> = _fcmToken

    var showBottomSheet by mutableStateOf(false)
    var showLanguageDialog by mutableStateOf(false)

    fun sendFcmToken(fcmToken: String) {
        performApiCall(
            response = _fcmToken,
            call = { RetrofitInstance.get().api.sendFcmToken(Token(fcmToken)) }
        )
    }

    fun onEvent(event: MainEvent) {
        stateApp = when(event) {
            is MainEvent.ThemeChange -> {
                stateApp?.copy(theme = event.theme)
            }

            is MainEvent.LanguageChange -> {
                stateApp?.copy(language = event.language)
            }
        }
    }
}

sealed class MainEvent {
    data class ThemeChange(val theme: AppThemeEnum): MainEvent()
    data class LanguageChange(val language: String): MainEvent()
}

data class MainState(
    val theme: AppThemeEnum,
    val language: String
)