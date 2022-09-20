package com.orlik.converter.tools

import java.math.BigDecimal

class UnitConverter {
    companion object {
        fun convertUnit(value: BigDecimal, key: String, data: Map<String, Float>): BigDecimal =
            value.times(data[key]!!.toBigDecimal())
    }
}