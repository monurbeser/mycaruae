package com.mycaruae.app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mycaruae.app.data.database.entity.EmirateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmirateDao {
    @Query("SELECT * FROM emirates ORDER BY nameEn ASC")
    fun getAll(): Flow<List<EmirateEntity>>

    @Query("SELECT * FROM emirates WHERE id = :id")
    fun getById(id: String): Flow<EmirateEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(emirates: List<EmirateEntity>)
}
