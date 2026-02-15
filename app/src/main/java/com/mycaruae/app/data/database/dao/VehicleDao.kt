package com.mycaruae.app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mycaruae.app.data.database.entity.VehicleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicles WHERE userId = :userId LIMIT 1")
    fun getByUserId(userId: String): Flow<VehicleEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicle: VehicleEntity)

    @Update
    suspend fun update(vehicle: VehicleEntity)

    @Query("DELETE FROM vehicles WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("UPDATE vehicles SET currentMileage = :mileage, updatedAt = :updatedAt WHERE id = :vehicleId")
    suspend fun updateMileage(vehicleId: String, mileage: Int, updatedAt: Long)

    @Query("UPDATE vehicles SET registrationExpiry = :expiry, updatedAt = :updatedAt WHERE id = :vehicleId")
    suspend fun updateRegistrationExpiry(vehicleId: String, expiry: Long, updatedAt: Long)

    @Query("UPDATE vehicles SET inspectionExpiry = :expiry, updatedAt = :updatedAt WHERE id = :vehicleId")
    suspend fun updateInspectionExpiry(vehicleId: String, expiry: Long, updatedAt: Long)
}
