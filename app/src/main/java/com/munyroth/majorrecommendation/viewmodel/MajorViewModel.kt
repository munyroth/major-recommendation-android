package com.munyroth.majorrecommendation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.munyroth.majorrecommendation.api.RetrofitInstance
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.Major
import com.munyroth.majorrecommendation.model.ResData
import com.munyroth.majorrecommendation.model.Status

class MajorViewModel : BaseViewModel() {
    // LiveData
    private val _majors = mutableStateOf(ApiData<ResData<List<Major>>>(Status.LOADING, null))
    val majors: MutableState<ApiData<ResData<List<Major>>>> = _majors

    // Load majors
    fun loadMajors(search: String? = null) {
        performApiCall(
            response = _majors,
            call = { RetrofitInstance.get().api.getMajors(search = search) }
        )
    }
}