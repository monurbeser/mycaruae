package com.mycaruae.app.data.repository;

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
public final class VehicleRepository_Factory implements Factory<VehicleRepository> {
  private final Provider<VehicleDao> vehicleDaoProvider;

  public VehicleRepository_Factory(Provider<VehicleDao> vehicleDaoProvider) {
    this.vehicleDaoProvider = vehicleDaoProvider;
  }

  @Override
  public VehicleRepository get() {
    return newInstance(vehicleDaoProvider.get());
  }

  public static VehicleRepository_Factory create(Provider<VehicleDao> vehicleDaoProvider) {
    return new VehicleRepository_Factory(vehicleDaoProvider);
  }

  public static VehicleRepository newInstance(VehicleDao vehicleDao) {
    return new VehicleRepository(vehicleDao);
  }
}
