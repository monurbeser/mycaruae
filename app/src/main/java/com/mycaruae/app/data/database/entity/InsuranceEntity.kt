package com.mycaruae.app.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "insurance",
    foreignKeys = [
        ForeignKey(
            entity = VehicleEntity::class,
            parentColumns = ["id"],
            childColumns = ["vehicleId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("vehicleId")],
)
data class InsuranceEntity(
    @PrimaryKey val id: String,
    val vehicleId: String,
    val companyName: String,
    val type: String, // "THIRD_PARTY" or "COMPREHENSIVE"
    val startDate: Long,
    val endDate: Long,
    val documentUri: String? = null,
    val status: String = "ACTIVE", // "ACTIVE" or "EXPIRED"
    val pendingSync: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long,
)