package com.mycaruae.app.feature.reminders;

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
public final class ReminderCreateViewModel_Factory implements Factory<ReminderCreateViewModel> {
  private final Provider<ReminderRepository> reminderRepositoryProvider;

  private final Provider<VehicleRepository> vehicleRepositoryProvider;

  private final Provider<UserPreferences> userPreferencesProvider;

  public ReminderCreateViewModel_Factory(Provider<ReminderRepository> reminderRepositoryProvider,
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    this.reminderRepositoryProvider = reminderRepositoryProvider;
    this.vehicleRepositoryProvider = vehicleRepositoryProvider;
    this.userPreferencesProvider = userPreferencesProvider;
  }

  @Override
  public ReminderCreateViewModel get() {
    return newInstance(reminderRepositoryProvider.get(), vehicleRepositoryProvider.get(), userPreferencesProvider.get());
  }

  public static ReminderCreateViewModel_Factory create(
      Provider<ReminderRepository> reminderRepositoryProvider,
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    return new ReminderCreateViewModel_Factory(reminderRepositoryProvider, vehicleRepositoryProvider, userPreferencesProvider);
  }

  public static ReminderCreateViewModel newInstance(ReminderRepository reminderRepository,
      VehicleRepository vehicleRepository, UserPreferences userPreferences) {
    return new ReminderCreateViewModel(reminderRepository, vehicleRepository, userPreferences);
  }
}
