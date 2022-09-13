package com.orlik.converter

class UnitConverter {
    companion object {
        fun convertUnit(value: Float, key: String, data: Map<String, Float>): Float = value * data[key]!!
    }
}