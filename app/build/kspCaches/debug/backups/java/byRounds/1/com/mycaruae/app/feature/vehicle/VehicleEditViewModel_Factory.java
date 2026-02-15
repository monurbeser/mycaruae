package com.mycaruae.app.feature.vehicle;

import com.mycaruae.app.data.datastore.UserPreferences;
import com.mycaruae.app.data.repository.BrandRepository;
import com.mycaruae.app.data.repository.EmirateRepository;
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
public final class VehicleEditViewModel_Factory implements Factory<VehicleEditViewModel> {
  private final Provider<VehicleRepository> vehicleRepositoryProvider;

  private final Provider<BrandRepository> brandRepositoryProvider;

  private final Provider<EmirateRepository> emirateRepositoryProvider;

  private final Provider<ReminderRepository> reminderRepositoryProvider;

  private final Provider<UserPreferences> userPreferencesProvider;

  public VehicleEditViewModel_Factory(Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<BrandRepository> brandRepositoryProvider,
      Provider<EmirateRepository> emirateRepositoryProvider,
      Provider<ReminderRepository> reminderRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    this.vehicleRepositoryProvider = vehicleRepositoryProvider;
    this.brandRepositoryProvider = brandRepositoryProvider;
    this.emirateRepositoryProvider = emirateRepositoryProvider;
    this.reminderRepositoryProvider = reminderRepositoryProvider;
    this.userPreferencesProvider = userPreferencesProvider;
  }

  @Override
  public VehicleEditViewModel get() {
    return newInstance(vehicleRepositoryProvider.get(), brandRepositoryProvider.get(), emirateRepositoryProvider.get(), reminderRepositoryProvider.get(), userPreferencesProvider.get());
  }

  public static VehicleEditViewModel_Factory create(
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<BrandRepository> brandRepositoryProvider,
      Provider<EmirateRepository> emirateRepositoryProvider,
      Provider<ReminderRepository> reminderRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    return new VehicleEditViewModel_Factory(vehicleRepositoryProvider, brandRepositoryProvider, emirateRepositoryProvider, reminderRepositoryProvider, userPreferencesProvider);
  }

  public static VehicleEditViewModel newInstance(VehicleRepository vehicleRepository,
      BrandRepository brandRepository, EmirateRepository emirateRepository,
      ReminderRepository reminderRepository, UserPreferences userPreferences) {
    return new VehicleEditViewModel(vehicleRepository, brandRepository, emirateRepository, reminderRepository, userPreferences);
  }
}
