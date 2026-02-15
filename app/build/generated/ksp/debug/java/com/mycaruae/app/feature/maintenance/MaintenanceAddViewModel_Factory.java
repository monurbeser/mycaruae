package com.mycaruae.app.feature.maintenance;

import com.mycaruae.app.data.datastore.UserPreferences;
import com.mycaruae.app.data.repository.MaintenanceRepository;
import com.mycaruae.app.data.repository.VehicleRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class MaintenanceAddViewModel_Factory implements Factory<MaintenanceAddViewModel> {
  private final Provider<MaintenanceRepository> maintenanceRepositoryProvider;

  private final Provider<VehicleRepository> vehicleRepositoryProvider;

  private final Provider<UserPreferences> userPreferencesProvider;

  public MaintenanceAddViewModel_Factory(
      Provider<MaintenanceRepository> maintenanceRepositoryProvider,
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    this.maintenanceRepositoryProvider = maintenanceRepositoryProvider;
    this.vehicleRepositoryProvider = vehicleRepositoryProvider;
    this.userPreferencesProvider = userPreferencesProvider;
  }

  @Override
  public MaintenanceAddViewModel get() {
    return newInstance(maintenanceRepositoryProvider.get(), vehicleRepositoryProvider.get(), userPreferencesProvider.get());
  }

  public static MaintenanceAddViewModel_Factory create(
      Provider<MaintenanceRepository> maintenanceRepositoryProvider,
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    return new MaintenanceAddViewModel_Factory(maintenanceRepositoryProvider, vehicleRepositoryProvider, userPreferencesProvider);
  }

  public static MaintenanceAddViewModel newInstance(MaintenanceRepository maintenanceRepository,
      VehicleRepository vehicleRepository, UserPreferences userPreferences) {
    return new MaintenanceAddViewModel(maintenanceRepository, vehicleRepository, userPreferences);
  }
}
