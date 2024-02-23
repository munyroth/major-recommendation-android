package com.munyroth.majorrecommendation.model

data class University(
    val id: Int,
    val universityType: String,
    val nameKm: String,
    val nameEn: String,
    val aboutKm: String,
    val aboutEn: String,
    val logo: String,
    val website: String,
    val email: String,
    val phone: String,
    val createdAt: String,
    val updatedAt: String
)