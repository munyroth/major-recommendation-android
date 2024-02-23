package com.munyroth.majorrecommendation.request

data class RecommendationRequest (
    var khmer: Float = 0f,
    var english: Float = 0f,
    var maths: Float = 0f,
    var physics: Float = 0f,
    var chemistry: Float = 0f,
    var biology: Float = 0f,
    var history: Float = 0f,
    var geography: Float = 0f,
    var morality: Float = 0f
)