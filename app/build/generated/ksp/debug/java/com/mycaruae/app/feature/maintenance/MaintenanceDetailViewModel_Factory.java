package com.mycaruae.app.feature.maintenance;

import androidx.lifecycle.SavedStateHandle;
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
public final class MaintenanceDetailViewModel_Factory implements Factory<MaintenanceDetailViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<MaintenanceRepository> maintenanceRepositoryProvider;

  private final Provider<VehicleRepository> vehicleRepositoryProvider;

  private final Provider<UserPreferences> userPreferencesProvider;

  public MaintenanceDetailViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<MaintenanceRepository> maintenanceRepositoryProvider,
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.maintenanceRepositoryProvider = maintenanceRepositoryProvider;
    this.vehicleRepositoryProvider = vehicleRepositoryProvider;
    this.userPreferencesProvider = userPreferencesProvider;
  }

  @Override
  public MaintenanceDetailViewModel get() {
    return newInstance(savedStateHandleProvider.get(), maintenanceRepositoryProvider.get(), vehicleRepositoryProvider.get(), userPreferencesProvider.get());
  }

  public static MaintenanceDetailViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<MaintenanceRepository> maintenanceRepositoryProvider,
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    return new MaintenanceDetailViewModel_Factory(savedStateHandleProvider, maintenanceRepositoryProvider, vehicleRepositoryProvider, userPreferencesProvider);
  }

  public static MaintenanceDetailViewModel newInstance(SavedStateHandle savedStateHandle,
      MaintenanceRepository maintenanceRepository, VehicleRepository vehicleRepository,
      UserPreferences userPreferences) {
    return new MaintenanceDetailViewModel(savedStateHandle, maintenanceRepository, vehicleRepository, userPreferences);
  }
}
