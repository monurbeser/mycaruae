package com.mycaruae.app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mycaruae.app.data.database.entity.RenewalLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RenewalLogDao {
    @Query("SELECT * FROM renewal_logs WHERE vehicleId = :vehicleId ORDER BY renewalDate DESC")
    fun getByVehicle(vehicleId: String): Flow<List<RenewalLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: RenewalLogEntity)
}
