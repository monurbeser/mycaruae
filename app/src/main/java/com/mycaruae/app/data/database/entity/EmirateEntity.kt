package com.mycaruae.app.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emirates")
data class EmirateEntity(
    @PrimaryKey val id: String,
    val nameEn: String,
    val trafficAuthority: String,
    val registrationUrl: String,
    val inspectionUrl: String?,
    val phone: String?,
)
