package com.munyroth.majorrecommendation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.munyroth.majorrecommendation.api.RetrofitInstance
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.ResData
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.model.University

class UniversityViewModel : BaseViewModel(){
    // LiveData
    private val _universities = mutableStateOf(ApiData<ResData<List<University>>>(Status.LOADING, null))
    val universities: MutableState<ApiData<ResData<List<University>>>> = _universities

    // Load universities
    fun loadUniversities(search : String? = null) {
        performApiCall(
            response = _universities,
            call = { RetrofitInstance.get().api.getUniversities(search) }
        )
    }

    // Search universities
    fun searchUniversities(search : String): List<University> {
        return universities.value.data?.data?.filter {
            it.nameEn.contains(search, true)
        } ?: emptyList()
    }
}