package com.munyroth.majorrecommendation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.munyroth.majorrecommendation.api.RetrofitInstance
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.Recommendation
import com.munyroth.majorrecommendation.model.ResData
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.request.RecommendationRequest

class RecommendationViewModel : BaseViewModel() {
    // LiveData
    private val _recommendation = MutableLiveData<ApiData<ResData<List<Recommendation>>>>()
    val recommendation: LiveData<ApiData<ResData<List<Recommendation>>>> = _recommendation

    // Recommendation
    fun recommendation(
        request: RecommendationRequest
    ) {
        performApiCall(
            response = _recommendation,
            call = { RetrofitInstance.get().api.recommendation(request) }
        )
    }

    // Change recommendation status
    fun changeRecommendationStatus(status: Status) {
        _recommendation.value = _recommendation.value?.copy(status = status)
    }
}