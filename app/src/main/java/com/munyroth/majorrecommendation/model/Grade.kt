package com.munyroth.majorrecommendation.model

data class Grade(val name: String, val value: Int) {
    override fun toString(): String {
        return name
    }
}
