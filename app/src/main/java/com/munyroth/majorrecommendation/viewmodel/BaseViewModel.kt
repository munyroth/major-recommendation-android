package com.munyroth.majorrecommendation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

open class BaseViewModel : ViewModel() {

    inline fun <reified T> performApiCall(
        response: MutableLiveData<ApiData<T>>,
        crossinline call: suspend () -> Response<T>,
        crossinline onSuccess: (T) -> ApiData<T> = { data -> ApiData(Status.SUCCESS, data) },
        crossinline onError: (Response<T>) -> ApiData<T> = { ApiData(Status.ERROR, null) }
    ) {
        // Initial response data with Processing status
        var responseData: ApiData<T>

        // Check if the response LiveData already has a value
        response.value?.let {
            // If yes, set LoadingMore status and update the LiveData
            responseData = ApiData(Status.LOADING_MORE, it.data)
            response.postValue(responseData)
        } ?: response.postValue(ApiData(Status.LOADING, null)) // If no, set Processing status

        // Launch a coroutine in the IO dispatcher
        viewModelScope.launch(Dispatchers.IO) {
            responseData = try {
                // Make the API call
                val res = call.invoke()

                // Check the HTTP status code of the response
                when (res.code()) {
                    in 200..204 -> {
                        Log.d("BaseViewModel", "Success: ${res.body()}")
                        onSuccess(res.body()!!)
                    }

                    401 -> onError(res)
                    403 -> ApiData(Status.NEED_VERIFY, null)

                    in 400..499 -> {
                        val errorBody = res.errorBody()?.string() ?: "Unknown error"
                        val errorResAuth = Gson().fromJson(errorBody, T::class.java)

                        Log.e("BaseViewModel", "Error: $errorBody")
                        ApiData(Status.ERROR, errorResAuth)
                    }

                    else -> {
                        Log.e("BaseViewModel", "Error: ${res.errorBody()?.string()}")
                        onError(res)
                    }
                }
            } catch (e: Exception) {
                Log.e("BaseViewModel", "Error: ${e.message}")
                ApiData(Status.ERROR, null)
            }

            withContext(Dispatchers.Main.immediate) {
                response.value = responseData
            }
        }
    }
}
