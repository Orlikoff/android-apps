package com.orlik.converter

object ConvertingRules {
    val weightsRules = mapOf<String, Float>(
        "KgKg" to 1f, "KgOz" to 35.247f, "KgKt" to 5000f,
        "OzOz" to 1f, "OzKg" to 0.0283495f, "OzKt" to 141.748f,
        "KtKt" to 1f, "KtKg" to 0.0002f, "KtOz" to 0.00705479f
    )
}