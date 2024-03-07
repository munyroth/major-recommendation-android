package com.munyroth.majorrecommendation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.munyroth.majorrecommendation.api.RetrofitInstance
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.ResData
import com.munyroth.majorrecommendation.model.Token

class MainViewModel : BaseViewModel() {
    // LiveData
    private val _fcmToken = MutableLiveData<ApiData<ResData<String>>>()
    val fcmToken: LiveData<ApiData<ResData<String>>> = _fcmToken

    fun sendFcmToken(fcmToken: String) {
        performApiCall(
            response = _fcmToken,
            call = { RetrofitInstance.get().api.sendFcmToken(Token(fcmToken)) }
        )
    }
}