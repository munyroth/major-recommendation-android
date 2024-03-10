package com.munyroth.majorrecommendation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.munyroth.majorrecommendation.api.RetrofitInstance
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.Recommendation
import com.munyroth.majorrecommendation.model.ResData
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.request.RecommendationRequest

class RecommendationViewModel : BaseViewModel() {
    // State
    private val _recommendation = mutableStateOf(ApiData<ResData<List<Recommendation>>>(Status.LOADING, null))
    val recommendation: MutableState<ApiData<ResData<List<Recommendation>>>> = _recommendation

    // Recommendation
    fun recommendation(
        request: RecommendationRequest
    ) {
        performApiCall<ResData<List<Recommendation>>>(
            response = _recommendation,
            call = { RetrofitInstance.get().api.recommendation(request) }
        )
    }


    // Change recommendation status
    fun changeRecommendationStatus(status: Status) {
        _recommendation.value = _recommendation.value.copy(status = status)
    }
}