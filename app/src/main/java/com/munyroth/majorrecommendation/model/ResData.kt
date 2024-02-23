package com.munyroth.majorrecommendation.model

data class ResData<T>(
    val status: Int,
    val message: String,
    val data: T,
    val meta: Meta?
)