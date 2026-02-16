package com.mycaruae.app.feature.dashboard;

import com.mycaruae.app.data.datastore.UserPreferences;
import com.mycaruae.app.data.repository.BrandRepository;
import com.mycaruae.app.data.repository.MileageRepository;
import com.mycaruae.app.data.repository.ReminderRepository;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<VehicleRepository> vehicleRepositoryProvider;

  private final Provider<BrandRepository> brandRepositoryProvider;

  private final Provider<MileageRepository> mileageRepositoryProvider;

  private final Provider<ReminderRepository> reminderRepositoryProvider;

  private final Provider<UserPreferences> userPreferencesProvider;

  public DashboardViewModel_Factory(Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<BrandRepository> brandRepositoryProvider,
      Provider<MileageRepository> mileageRepositoryProvider,
      Provider<ReminderRepository> reminderRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    this.vehicleRepositoryProvider = vehicleRepositoryProvider;
    this.brandRepositoryProvider = brandRepositoryProvider;
    this.mileageRepositoryProvider = mileageRepositoryProvider;
    this.reminderRepositoryProvider = reminderRepositoryProvider;
    this.userPreferencesProvider = userPreferencesProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(vehicleRepositoryProvider.get(), brandRepositoryProvider.get(), mileageRepositoryProvider.get(), reminderRepositoryProvider.get(), userPreferencesProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<BrandRepository> brandRepositoryProvider,
      Provider<MileageRepository> mileageRepositoryProvider,
      Provider<ReminderRepository> reminderRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    return new DashboardViewModel_Factory(vehicleRepositoryProvider, brandRepositoryProvider, mileageRepositoryProvider, reminderRepositoryProvider, userPreferencesProvider);
  }

  public static DashboardViewModel newInstance(VehicleRepository vehicleRepository,
      BrandRepository brandRepository, MileageRepository mileageRepository,
      ReminderRepository reminderRepository, UserPreferences userPreferences) {
    return new DashboardViewModel(vehicleRepository, brandRepository, mileageRepository, reminderRepository, userPreferences);
  }
}
