package com.mycaruae.app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mycaruae.app.data.database.entity.MaintenanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MaintenanceDao {
    @Query("SELECT * FROM maintenance_records WHERE vehicleId = :vehicleId ORDER BY serviceDate DESC")
    fun getByVehicle(vehicleId: String): Flow<List<MaintenanceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: MaintenanceEntity)

    @Update
    suspend fun update(record: MaintenanceEntity)

    @Query("DELETE FROM maintenance_records WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT SUM(cost) FROM maintenance_records WHERE vehicleId = :vehicleId")
    fun getTotalCost(vehicleId: String): Flow<Double?>
}
