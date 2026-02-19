package com.mycaruae.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mycaruae.app.data.database.dao.BrandDao
import com.mycaruae.app.data.database.dao.EmirateDao
import com.mycaruae.app.data.database.dao.InsuranceDao
import com.mycaruae.app.data.database.dao.MaintenanceDao
import com.mycaruae.app.data.database.dao.MileageLogDao
import com.mycaruae.app.data.database.dao.ReminderDao
import com.mycaruae.app.data.database.dao.RenewalLogDao
import com.mycaruae.app.data.database.dao.VehicleDao
import com.mycaruae.app.data.database.entity.BrandEntity
import com.mycaruae.app.data.database.entity.EmirateEntity
import com.mycaruae.app.data.database.entity.InsuranceEntity
import com.mycaruae.app.data.database.entity.MaintenanceEntity
import com.mycaruae.app.data.database.entity.MileageLogEntity
import com.mycaruae.app.data.database.entity.ReminderEntity
import com.mycaruae.app.data.database.entity.RenewalLogEntity
import com.mycaruae.app.data.database.entity.VehicleEntity

@Database(
    entities = [
        EmirateEntity::class,
        BrandEntity::class,
        VehicleEntity::class,
        MileageLogEntity::class,
        MaintenanceEntity::class,
        ReminderEntity::class,
        RenewalLogEntity::class,
        InsuranceEntity::class,
    ],
    version = 2,
    exportSchema = true,
)
abstract class CocDatabase : RoomDatabase() {
    abstract fun emirateDao(): EmirateDao
    abstract fun brandDao(): BrandDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun mileageLogDao(): MileageLogDao
    abstract fun maintenanceDao(): MaintenanceDao
    abstract fun reminderDao(): ReminderDao
    abstract fun renewalLogDao(): RenewalLogDao
    abstract fun insuranceDao(): InsuranceDao
}