package com.mycaruae.app.data.repository;

import com.mycaruae.app.data.database.dao.MaintenanceDao;
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
public final class MaintenanceRepository_Factory implements Factory<MaintenanceRepository> {
  private final Provider<MaintenanceDao> maintenanceDaoProvider;

  public MaintenanceRepository_Factory(Provider<MaintenanceDao> maintenanceDaoProvider) {
    this.maintenanceDaoProvider = maintenanceDaoProvider;
  }

  @Override
  public MaintenanceRepository get() {
    return newInstance(maintenanceDaoProvider.get());
  }

  public static MaintenanceRepository_Factory create(
      Provider<MaintenanceDao> maintenanceDaoProvider) {
    return new MaintenanceRepository_Factory(maintenanceDaoProvider);
  }

  public static MaintenanceRepository newInstance(MaintenanceDao maintenanceDao) {
    return new MaintenanceRepository(maintenanceDao);
  }
}
