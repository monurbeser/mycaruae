package com.mycaruae.app.feature.settings;

import com.mycaruae.app.data.datastore.UserPreferences;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<UserPreferences> userPreferencesProvider;

  private final Provider<VehicleRepository> vehicleRepositoryProvider;

  private final Provider<ReminderRepository> reminderRepositoryProvider;

  public SettingsViewModel_Factory(Provider<UserPreferences> userPreferencesProvider,
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<ReminderRepository> reminderRepositoryProvider) {
    this.userPreferencesProvider = userPreferencesProvider;
    this.vehicleRepositoryProvider = vehicleRepositoryProvider;
    this.reminderRepositoryProvider = reminderRepositoryProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(userPreferencesProvider.get(), vehicleRepositoryProvider.get(), reminderRepositoryProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<UserPreferences> userPreferencesProvider,
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<ReminderRepository> reminderRepositoryProvider) {
    return new SettingsViewModel_Factory(userPreferencesProvider, vehicleRepositoryProvider, reminderRepositoryProvider);
  }

  public static SettingsViewModel newInstance(UserPreferences userPreferences,
      VehicleRepository vehicleRepository, ReminderRepository reminderRepository) {
    return new SettingsViewModel(userPreferences, vehicleRepository, reminderRepository);
  }
}
