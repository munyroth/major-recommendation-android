package com.munyroth.majorrecommendation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.munyroth.majorrecommendation.api.RetrofitInstance
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.ResData
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.model.University

class UniversityDetailViewModel : BaseViewModel(){
    // LiveData
    private val _university = mutableStateOf(ApiData<ResData<University>>(Status.LOADING, null))
    val university: MutableState<ApiData<ResData<University>>> = _university

    // Load universities
    fun loadUniversity(id : Int) {
        performApiCall(
            response = _university,
            call = { RetrofitInstance.get().api.getUniversityById(id) }
        )
    }
}