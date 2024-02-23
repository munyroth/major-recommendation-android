package com.munyroth.majorrecommendation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.munyroth.majorrecommendation.api.RetrofitInstance
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.Major
import com.munyroth.majorrecommendation.model.ResData

class MajorViewModel : BaseViewModel(){
    // LiveData
    private val _majors = MutableLiveData<ApiData<ResData<List<Major>>>>()
    val majors: LiveData<ApiData<ResData<List<Major>>>> = _majors

    // Load majors
    fun loadMajors(search : String? = null) {
        performApiCall(
            response = _majors,
            call = { RetrofitInstance.get().api.getMajors(search) }
        )
    }
}