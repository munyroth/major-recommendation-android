package com.munyroth.majorrecommendation.model

data class University(
    val id: Int,
    val universityType: String,
    val nameKm: String,
    val nameEn: String,
    val aboutKm: String,
    val aboutEn: String,
    val logo: String,
    val website: String?,
    val email: String?,
    val phone: String?,
    val createdAt: String,
    val updatedAt: String,
    val faculties: List<Faculty>? = null
)

data class Faculty(
    val id: Int,
    val nameKm: String,
    val nameEn: String,
    val createdAt: String,
    val updatedAt: String,
    val departments: List<Department>
)

data class Department(
    val id: Int,
    val nameKm: String,
    val nameEn: String,
    val createdAt: String,
    val updatedAt: String,
)