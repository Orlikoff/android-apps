package com.orlik.converter.tools

object ConvertingRules {
    val weightsRules = mapOf<String, Float>(
        "KgKg" to 1f, "KgOz" to 35.247f, "KgKt" to 5000f,
        "OzOz" to 1f, "OzKg" to 0.0283495f, "OzKt" to 141.748f,
        "KtKt" to 1f, "KtKg" to 0.0002f, "KtOz" to 0.00705479f
    )

    val distanceRules = mapOf<String, Float> (
        "MtMt" to 1f, "MtYd" to 1.0936132983f, "MtIn" to 39.37007874f,
        "YdYd" to 1f, "YdMt" to 0.9144f, "YdIn" to 36f,
        "InIn" to 1f, "InMt" to 0.0254f, "InYd" to 0.0277777778f
    )

    val volumeRules = mapOf<String, Float>(
        "LtLt" to 1f, "LtGl" to 0.2641721769f, "LtPt" to 2.1133774149f,
        "GlGl" to 1f, "GlLt" to 3.78541f, "GlPt" to 8f,
        "PtPt" to 1f, "PtLt" to 0.47317625f, "PtGl" to 0.125f
    )
}