package com.mycaruae.app.data.model

enum class ReminderStatus {
    PENDING, SNOOZED, COMPLETED, OVERDUE
}

enum class ReminderSeverity {
    INFO, WARNING, URGENT, CRITICAL, OVERDUE
}

enum class RenewalType {
    REGISTRATION, INSPECTION
}
