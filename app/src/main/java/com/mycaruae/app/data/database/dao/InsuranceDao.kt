package com.mycaruae.app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mycaruae.app.data.database.entity.InsuranceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InsuranceDao {
    @Query("SELECT * FROM insurance WHERE vehicleId = :vehicleId ORDER BY endDate DESC")
    fun getByVehicle(vehicleId: String): Flow<List<InsuranceEntity>>

    @Query("SELECT * FROM insurance ORDER BY endDate DESC")
    fun getAll(): Flow<List<InsuranceEntity>>

    @Query("SELECT * FROM insurance WHERE id = :id")
    suspend fun getById(id: String): InsuranceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(insurance: InsuranceEntity)

    @Update
    suspend fun update(insurance: InsuranceEntity)

    @Query("DELETE FROM insurance WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("UPDATE insurance SET status = 'EXPIRED', updatedAt = :now WHERE endDate < :now AND status = 'ACTIVE'")
    suspend fun expireOld(now: Long)
}