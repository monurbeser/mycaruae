package com.mycaruae.app.data.repository

import com.mycaruae.app.data.database.dao.InsuranceDao
import com.mycaruae.app.data.database.entity.InsuranceEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsuranceRepository @Inject constructor(
    private val insuranceDao: InsuranceDao,
) {
    fun getByVehicle(vehicleId: String): Flow<List<InsuranceEntity>> =
        insuranceDao.getByVehicle(vehicleId)

    fun getAll(): Flow<List<InsuranceEntity>> = insuranceDao.getAll()

    suspend fun getById(id: String): InsuranceEntity? = insuranceDao.getById(id)

    suspend fun add(insurance: InsuranceEntity) = insuranceDao.insert(insurance)

    suspend fun update(insurance: InsuranceEntity) = insuranceDao.update(insurance)

    suspend fun delete(id: String) = insuranceDao.deleteById(id)

    suspend fun expireOld() = insuranceDao.expireOld(System.currentTimeMillis())

    fun generateId(): String = UUID.randomUUID().toString()
}