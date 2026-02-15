package com.mycaruae.app.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val brandId: String,
    val modelId: String,
    val year: Int,
    val emirate: String,
    val registrationExpiry: Long,
    val inspectionExpiry: Long,
    val vin: String?,
    val plateNumber: String?,
    val color: String?,
    val currentMileage: Int,
    val photoUris: String? = null, // Comma-separated URI strings
    val pendingSync: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long,
)