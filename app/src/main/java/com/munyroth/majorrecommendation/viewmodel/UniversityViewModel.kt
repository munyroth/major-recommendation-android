package com.munyroth.majorrecommendation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.munyroth.majorrecommendation.api.RetrofitInstance
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.ResData
import com.munyroth.majorrecommendation.model.University

class UniversityViewModel : BaseViewModel(){
    // LiveData
    private val _universities = MutableLiveData<ApiData<ResData<List<University>>>>()
    val universities: LiveData<ApiData<ResData<List<University>>>> = _universities

    // Load universities
    fun loadUniversities(search : String? = null) {
        performApiCall(
            response = _universities,
            call = { RetrofitInstance.get().api.getUniversities(search) }
        )
    }
}