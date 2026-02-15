package com.mycaruae.app.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mycaruae.app.data.database.CocDatabase
import com.mycaruae.app.data.database.dao.BrandDao
import com.mycaruae.app.data.database.dao.EmirateDao
import com.mycaruae.app.data.database.dao.MaintenanceDao
import com.mycaruae.app.data.database.dao.MileageLogDao
import com.mycaruae.app.data.database.dao.ReminderDao
import com.mycaruae.app.data.database.dao.RenewalLogDao
import com.mycaruae.app.data.database.dao.VehicleDao
import com.mycaruae.app.data.database.seed.SeedData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        emirateDao: Provider<EmirateDao>,
        brandDao: Provider<BrandDao>,
    ): CocDatabase {
        return Room.databaseBuilder(
            context,
            CocDatabase::class.java,
            "mycaruae.db",
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                        emirateDao.get().insertAll(SeedData.emirates)
                        brandDao.get().insertAll(SeedData.sampleBrands)
                    }
                }
            })
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides fun provideEmirateDao(db: CocDatabase): EmirateDao = db.emirateDao()
    @Provides fun provideBrandDao(db: CocDatabase): BrandDao = db.brandDao()
    @Provides fun provideVehicleDao(db: CocDatabase): VehicleDao = db.vehicleDao()
    @Provides fun provideMileageLogDao(db: CocDatabase): MileageLogDao = db.mileageLogDao()
    @Provides fun provideMaintenanceDao(db: CocDatabase): MaintenanceDao = db.maintenanceDao()
    @Provides fun provideReminderDao(db: CocDatabase): ReminderDao = db.reminderDao()
    @Provides fun provideRenewalLogDao(db: CocDatabase): RenewalLogDao = db.renewalLogDao()
}
