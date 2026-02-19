package com.mycaruae.app.feature.reminders;

import com.mycaruae.app.data.datastore.UserPreferences;
import com.mycaruae.app.data.repository.BrandRepository;
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
public final class RemindersViewModel_Factory implements Factory<RemindersViewModel> {
  private final Provider<ReminderRepository> reminderRepositoryProvider;

  private final Provider<VehicleRepository> vehicleRepositoryProvider;

  private final Provider<BrandRepository> brandRepositoryProvider;

  private final Provider<UserPreferences> userPreferencesProvider;

  public RemindersViewModel_Factory(Provider<ReminderRepository> reminderRepositoryProvider,
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<BrandRepository> brandRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    this.reminderRepositoryProvider = reminderRepositoryProvider;
    this.vehicleRepositoryProvider = vehicleRepositoryProvider;
    this.brandRepositoryProvider = brandRepositoryProvider;
    this.userPreferencesProvider = userPreferencesProvider;
  }

  @Override
  public RemindersViewModel get() {
    return newInstance(reminderRepositoryProvider.get(), vehicleRepositoryProvider.get(), brandRepositoryProvider.get(), userPreferencesProvider.get());
  }

  public static RemindersViewModel_Factory create(
      Provider<ReminderRepository> reminderRepositoryProvider,
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<BrandRepository> brandRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    return new RemindersViewModel_Factory(reminderRepositoryProvider, vehicleRepositoryProvider, brandRepositoryProvider, userPreferencesProvider);
  }

  public static RemindersViewModel newInstance(ReminderRepository reminderRepository,
      VehicleRepository vehicleRepository, BrandRepository brandRepository,
      UserPreferences userPreferences) {
    return new RemindersViewModel(reminderRepository, vehicleRepository, brandRepository, userPreferences);
  }
}
