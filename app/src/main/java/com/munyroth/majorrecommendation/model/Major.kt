package com.munyroth.majorrecommendation.model

data class Major(
    val id: Int,
    val major: String,
    val subjects: List<String>
)
