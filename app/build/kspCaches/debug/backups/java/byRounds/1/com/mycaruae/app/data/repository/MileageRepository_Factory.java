package com.mycaruae.app.data.repository;

import com.mycaruae.app.data.database.dao.MileageLogDao;
import com.mycaruae.app.data.database.dao.VehicleDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class MileageRepository_Factory implements Factory<MileageRepository> {
  private final Provider<MileageLogDao> mileageLogDaoProvider;

  private final Provider<VehicleDao> vehicleDaoProvider;

  public MileageRepository_Factory(Provider<MileageLogDao> mileageLogDaoProvider,
      Provider<VehicleDao> vehicleDaoProvider) {
    this.mileageLogDaoProvider = mileageLogDaoProvider;
    this.vehicleDaoProvider = vehicleDaoProvider;
  }

  @Override
  public MileageRepository get() {
    return newInstance(mileageLogDaoProvider.get(), vehicleDaoProvider.get());
  }

  public static MileageRepository_Factory create(Provider<MileageLogDao> mileageLogDaoProvider,
      Provider<VehicleDao> vehicleDaoProvider) {
    return new MileageRepository_Factory(mileageLogDaoProvider, vehicleDaoProvider);
  }

  public static MileageRepository newInstance(MileageLogDao mileageLogDao, VehicleDao vehicleDao) {
    return new MileageRepository(mileageLogDao, vehicleDao);
  }
}
