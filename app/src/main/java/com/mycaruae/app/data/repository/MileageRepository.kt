package com.mycaruae.app.data.repository

import com.mycaruae.app.data.database.dao.MileageLogDao
import com.mycaruae.app.data.database.dao.VehicleDao
import com.mycaruae.app.data.database.entity.MileageLogEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MileageRepository @Inject constructor(
    private val mileageLogDao: MileageLogDao,
    private val vehicleDao: VehicleDao,
) {
    fun getHistory(vehicleId: String): Flow<List<MileageLogEntity>> {
        return mileageLogDao.getByVehicle(vehicleId)
    }

    suspend fun getLatest(vehicleId: String): MileageLogEntity? {
        return mileageLogDao.getLatest(vehicleId)
    }

    suspend fun addEntry(vehicleId: String, mileage: Int): MileageLogEntity {
        val now = System.currentTimeMillis()
        val entry = MileageLogEntity(
            id = UUID.randomUUID().toString(),
            vehicleId = vehicleId,
            mileage = mileage,
            recordedDate = now,
            pendingSync = true,
            createdAt = now,
        )
        mileageLogDao.insert(entry)
        vehicleDao.updateMileage(vehicleId, mileage, now)
        return entry
    }

    suspend fun deleteEntry(id: String) {
        mileageLogDao.deleteById(id)
    }
}