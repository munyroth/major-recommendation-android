package com.munyroth.majorrecommendation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.munyroth.majorrecommendation.api.RetrofitInstance
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.ResData
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.model.Token

class MainViewModel : BaseViewModel() {
    // LiveData
    private val _fcmToken = mutableStateOf(ApiData<ResData<String>>(Status.LOADING, null))
    val fcmToken: MutableState<ApiData<ResData<String>>> = _fcmToken

    fun sendFcmToken(fcmToken: String) {
        performApiCall(
            response = _fcmToken,
            call = { RetrofitInstance.get().api.sendFcmToken(Token(fcmToken)) }
        )
    }
}