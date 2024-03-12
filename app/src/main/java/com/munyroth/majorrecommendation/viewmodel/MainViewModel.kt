package com.munyroth.majorrecommendation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.munyroth.majorrecommendation.api.RetrofitInstance
import com.munyroth.majorrecommendation.core.AppCore
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.ResData
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.model.Token
import com.munyroth.majorrecommendation.model.enums.AppThemeEnum
import com.munyroth.majorrecommendation.utility.AppPreference

class MainViewModel : BaseViewModel() {
    private val _fcmToken = mutableStateOf(ApiData<ResData<String>>(Status.LOADING, null))
    val fcmToken: MutableState<ApiData<ResData<String>>> = _fcmToken

    var showBottomSheet by mutableStateOf(false)
    var showLanguageDialog by mutableStateOf(false)

    var stateApp by mutableStateOf(MainState())

    fun sendFcmToken(fcmToken: String) {
        performApiCall(
            response = _fcmToken,
            call = { RetrofitInstance.get().api.sendFcmToken(Token(fcmToken)) }
        )
    }

    fun onEvent(event: MainEvent) {
        when(event) {
            is MainEvent.ThemeChange -> {
                stateApp = stateApp.copy(theme = event.theme)
            }
            is MainEvent.LanguageChange -> {
                stateApp = stateApp.copy(language = event.language)
            }
            else -> { }
        }
    }
}

sealed class MainEvent {
    data class ThemeChange(val theme: AppThemeEnum): MainEvent()
    data class LanguageChange(val language: String): MainEvent()
}

data class MainState(
    val theme: AppThemeEnum = AppPreference.get(AppCore.get().applicationContext).getTheme(),
    val language: String = AppPreference.get(AppCore.get().applicationContext).getLanguage() ?: "km"
)