package com.mycaruae.app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mycaruae.app.data.database.entity.MileageLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MileageLogDao {
    @Query("SELECT * FROM mileage_logs WHERE vehicleId = :vehicleId ORDER BY recordedDate DESC")
    fun getByVehicle(vehicleId: String): Flow<List<MileageLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: MileageLogEntity)

    @Query("SELECT * FROM mileage_logs WHERE vehicleId = :vehicleId ORDER BY mileage DESC LIMIT 1")
    suspend fun getLatest(vehicleId: String): MileageLogEntity?

    @Query("DELETE FROM mileage_logs WHERE id = :id")
    suspend fun deleteById(id: String)
}