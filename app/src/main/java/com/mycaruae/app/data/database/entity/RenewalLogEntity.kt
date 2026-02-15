package com.mycaruae.app.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "renewal_logs",
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
data class RenewalLogEntity(
    @PrimaryKey val id: String,
    val vehicleId: String,
    val renewalType: String,
    val previousExpiry: Long,
    val newExpiry: Long,
    val renewalDate: Long,
    val cost: Double?,
    val note: String?,
    val pendingSync: Boolean = false,
    val createdAt: Long,
)
