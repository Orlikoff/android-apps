package com.orlik.converter

import android.widget.TextView

public enum class Modes{
    Weight, Distance, Volume
}

public object ModeTransfer {
    var currentMode:Modes = Modes.Weight
}