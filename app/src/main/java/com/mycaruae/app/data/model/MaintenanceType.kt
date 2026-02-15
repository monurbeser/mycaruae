package com.mycaruae.app.data.model

enum class MaintenanceType(val displayName: String, val icon: String) {
    OIL("Oil Change", "oil"),
    FILTER("Filter", "filter"),
    BRAKE_PAD("Brake Pads", "brake"),
    TIRE("Tires", "tire"),
    BATTERY("Battery", "battery"),
    AC_SERVICE("A/C Service", "ac"),
    TRANSMISSION("Transmission", "transmission"),
    COOLANT("Coolant Flush", "coolant"),
    ALIGNMENT("Wheel Alignment", "alignment"),
    WASH_DETAIL("Wash & Detail", "wash"),
    GENERAL("General Service", "general"),
    OTHER("Other", "other"),
}