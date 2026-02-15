package com.mycaruae.app.data.model

enum class MaintenanceType(val displayName: String) {
    OIL("Oil Change"),
    FILTER("Filter"),
    BRAKE_PAD("Brake Pads"),
    TIRE("Tires"),
    BATTERY("Battery"),
    GENERAL("General Service"),
    OTHER("Other"),
}
