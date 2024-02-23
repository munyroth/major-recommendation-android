package com.munyroth.majorrecommendation.api

import com.munyroth.majorrecommendation.core.AppCore
import com.munyroth.majorrecommendation.utility.AppPreference
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = AppPreference.get(AppCore.get().applicationContext).getToken()
        val originalRequest = chain.request()

        val request = originalRequest.newBuilder()
            .header("Accept", "application/json")
            .apply {
                if (token != null) {
                    addHeader("Authorization", "Bearer $token")
                }
            }
            .build()

        return chain.proceed(request)
    }
}