package com.munyroth.majorrecommendation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.munyroth.majorrecommendation.api.RetrofitInstance
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.ResData
import com.munyroth.majorrecommendation.model.University

class UniversityDetailViewModel : BaseViewModel(){
    // LiveData
    private val _university = MutableLiveData<ApiData<ResData<University>>>()
    val university: LiveData<ApiData<ResData<University>>> = _university

    // Load universities
    fun loadUniversity(id : Int) {
        performApiCall(
            response = _university,
            call = { RetrofitInstance.get().api.getUniversityById(id) }
        )
    }
}