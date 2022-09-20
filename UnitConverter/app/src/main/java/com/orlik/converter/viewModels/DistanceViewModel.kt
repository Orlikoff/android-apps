package com.orlik.converter.viewModels

import androidx.lifecycle.ViewModel
import java.math.BigDecimal

class DistanceViewModel : ViewModel() {
    var editValue: BigDecimal = BigDecimal(0)
    var resultValue: BigDecimal = BigDecimal(0)
    var convertFrom: Int = 2
    var convertTo: Int = 2
}