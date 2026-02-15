package com.mycaruae.app.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "maintenance_records",
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
data class MaintenanceEntity(
    @PrimaryKey val id: String,
    val vehicleId: String,
    val type: String,
    val description: String?,
    val serviceDate: Long,
    val mileageAtService: Int,
    val cost: Double,
    val serviceProvider: String?,
    val pendingSync: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long,
)
