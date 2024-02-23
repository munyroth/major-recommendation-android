package com.munyroth.majorrecommendation.model

data class ApiData<T>(
    val status: Status,
    val data: T?,
    val meta: Meta? = null
)

enum class Status{
    LOADING, SUCCESS, ERROR, LOADING_MORE, NEED_VERIFY
}