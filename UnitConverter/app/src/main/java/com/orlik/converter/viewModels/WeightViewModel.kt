package com.orlik.converter.viewModels

import androidx.lifecycle.ViewModel

class WeightViewModel : ViewModel() {
    var editValue: Float = 0.0f
    var resultValue: Float = 0.0f
    var convertFrom: Int = 2
    var convertTo: Int = 2
}