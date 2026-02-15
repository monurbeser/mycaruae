package com.mycaruae.app.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "mileage_logs",
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
data class MileageLogEntity(
    @PrimaryKey val id: String,
    val vehicleId: String,
    val mileage: Int,
    val recordedDate: Long,
    val pendingSync: Boolean = false,
    val createdAt: Long,
)
