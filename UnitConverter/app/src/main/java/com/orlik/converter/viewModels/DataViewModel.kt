package com.orlik.converter.viewModels

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.orlik.converter.R
import com.orlik.converter.unitFragments.WeightFragment

class DataViewModel : ViewModel() {
    var currentFragment: FragmentField = FragmentField.Weight
}

enum class FragmentField {
    Weight, Distance, Volume
}