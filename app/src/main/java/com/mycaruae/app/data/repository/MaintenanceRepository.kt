package com.mycaruae.app.data.repository

import com.mycaruae.app.data.database.dao.MaintenanceDao
import com.mycaruae.app.data.database.entity.MaintenanceEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MaintenanceRepository @Inject constructor(
    private val maintenanceDao: MaintenanceDao,
) {
    fun getHistory(vehicleId: String): Flow<List<MaintenanceEntity>> {
        return maintenanceDao.getByVehicle(vehicleId)
    }

    fun getTotalCost(vehicleId: String): Flow<Double?> {
        return maintenanceDao.getTotalCost(vehicleId)
    }

    suspend fun addRecord(
        vehicleId: String,
        type: String,
        description: String?,
        serviceDate: Long,
        mileageAtService: Int,
        cost: Double,
        serviceProvider: String?,
    ): MaintenanceEntity {
        val now = System.currentTimeMillis()
        val record = MaintenanceEntity(
            id = UUID.randomUUID().toString(),
            vehicleId = vehicleId,
            type = type,
            description = description,
            serviceDate = serviceDate,
            mileageAtService = mileageAtService,
            cost = cost,
            serviceProvider = serviceProvider,
            pendingSync = true,
            createdAt = now,
            updatedAt = now,
        )
        maintenanceDao.insert(record)
        return record
    }

    suspend fun deleteRecord(id: String) {
        maintenanceDao.deleteById(id)
    }
}