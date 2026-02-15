package com.mycaruae.app.data.repository

import com.mycaruae.app.data.database.dao.VehicleDao
import com.mycaruae.app.data.database.entity.VehicleEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleRepository @Inject constructor(
    private val vehicleDao: VehicleDao,
) {
    fun getVehicle(userId: String): Flow<VehicleEntity?> {
        return vehicleDao.getByUserId(userId)
    }

    suspend fun addVehicle(vehicle: VehicleEntity) {
        vehicleDao.insert(vehicle)
    }

    suspend fun updateVehicle(vehicle: VehicleEntity) {
        vehicleDao.update(vehicle)
    }

    suspend fun deleteVehicle(id: String) {
        vehicleDao.deleteById(id)
    }

    fun generateId(): String = UUID.randomUUID().toString()
}