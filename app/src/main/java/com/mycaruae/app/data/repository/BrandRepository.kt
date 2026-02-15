package com.mycaruae.app.data.repository

import com.mycaruae.app.data.database.dao.BrandDao
import com.mycaruae.app.data.database.entity.BrandEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BrandRepository @Inject constructor(
    private val brandDao: BrandDao,
) {
    fun getAllBrands(): Flow<List<BrandEntity>> = brandDao.getAll()
}