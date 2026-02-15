package com.mycaruae.app.data.repository

import com.mycaruae.app.data.database.dao.EmirateDao
import com.mycaruae.app.data.database.entity.EmirateEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmirateRepository @Inject constructor(
    private val emirateDao: EmirateDao,
) {
    fun getAllEmirates(): Flow<List<EmirateEntity>> = emirateDao.getAll()
}