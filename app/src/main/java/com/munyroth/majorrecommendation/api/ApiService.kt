package com.munyroth.majorrecommendation.api

import com.munyroth.majorrecommendation.model.Major
import com.munyroth.majorrecommendation.model.Recommendation
import com.munyroth.majorrecommendation.model.ResData
import com.munyroth.majorrecommendation.model.Token
import com.munyroth.majorrecommendation.model.University
import com.munyroth.majorrecommendation.request.RecommendationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // Endpoint for recommendation based on scores
    @POST("v1/recommendation")
    suspend fun recommendation(
        @Body recommendationRequest: RecommendationRequest
    ): Response<ResData<List<Recommendation>>>

    // Endpoint for getting majors
    @GET("v1/major_type")
    suspend fun getMajors(
        @Query("search") search: String? = null
    ): Response<ResData<List<Major>>>

    // Endpoint for getting universities
    @GET("v1/university")
    suspend fun getUniversities(
        @Query("search") search: String? = null
    ): Response<ResData<List<University>>>

    // Endpoint for getting university by id
    @GET("v1/university/{id}")
    suspend fun getUniversityById(
        @Path("id") id: Int
    ): Response<ResData<University>>

    // Endpoint for send fcm token
    @POST("fcm-token")
    suspend fun sendFcmToken(
        @Body token: Token
    ): Response<ResData<String>>
}