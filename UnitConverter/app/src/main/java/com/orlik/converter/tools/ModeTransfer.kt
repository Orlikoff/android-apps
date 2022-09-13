package com.orlik.converter.tools

public enum class Modes{
    Weight, Distance, Volume
}

public object ModeTransfer {
    var currentMode: Modes = Modes.Weight
}