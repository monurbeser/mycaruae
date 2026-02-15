package com.mycaruae.app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mycaruae.app.data.database.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders WHERE vehicleId = :vehicleId ORDER BY dueDate ASC")
    fun getByVehicle(vehicleId: String): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminders WHERE vehicleId = :vehicleId AND status = 'PENDING' ORDER BY dueDate ASC LIMIT :limit")
    fun getUpcoming(vehicleId: String, limit: Int = 5): Flow<List<ReminderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: ReminderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(reminders: List<ReminderEntity>)

    @Update
    suspend fun update(reminder: ReminderEntity)

    @Query("UPDATE reminders SET status = 'COMPLETED', completedAt = :completedAt, updatedAt = :updatedAt WHERE vehicleId = :vehicleId AND type = :type AND status != 'COMPLETED'")
    suspend fun completeByType(vehicleId: String, type: String, completedAt: Long, updatedAt: Long)
}
