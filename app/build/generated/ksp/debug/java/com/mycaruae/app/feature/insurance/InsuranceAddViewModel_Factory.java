package com.mycaruae.app.feature.insurance;

import com.mycaruae.app.data.datastore.UserPreferences;
import com.mycaruae.app.data.repository.BrandRepository;
import com.mycaruae.app.data.repository.InsuranceRepository;
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
public final class InsuranceAddViewModel_Factory implements Factory<InsuranceAddViewModel> {
  private final Provider<InsuranceRepository> insuranceRepositoryProvider;

  private final Provider<VehicleRepository> vehicleRepositoryProvider;

  private final Provider<BrandRepository> brandRepositoryProvider;

  private final Provider<ReminderRepository> reminderRepositoryProvider;

  private final Provider<UserPreferences> userPreferencesProvider;

  public InsuranceAddViewModel_Factory(Provider<InsuranceRepository> insuranceRepositoryProvider,
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<BrandRepository> brandRepositoryProvider,
      Provider<ReminderRepository> reminderRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    this.insuranceRepositoryProvider = insuranceRepositoryProvider;
    this.vehicleRepositoryProvider = vehicleRepositoryProvider;
    this.brandRepositoryProvider = brandRepositoryProvider;
    this.reminderRepositoryProvider = reminderRepositoryProvider;
    this.userPreferencesProvider = userPreferencesProvider;
  }

  @Override
  public InsuranceAddViewModel get() {
    return newInstance(insuranceRepositoryProvider.get(), vehicleRepositoryProvider.get(), brandRepositoryProvider.get(), reminderRepositoryProvider.get(), userPreferencesProvider.get());
  }

  public static InsuranceAddViewModel_Factory create(
      Provider<InsuranceRepository> insuranceRepositoryProvider,
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<BrandRepository> brandRepositoryProvider,
      Provider<ReminderRepository> reminderRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    return new InsuranceAddViewModel_Factory(insuranceRepositoryProvider, vehicleRepositoryProvider, brandRepositoryProvider, reminderRepositoryProvider, userPreferencesProvider);
  }

  public static InsuranceAddViewModel newInstance(InsuranceRepository insuranceRepository,
      VehicleRepository vehicleRepository, BrandRepository brandRepository,
      ReminderRepository reminderRepository, UserPreferences userPreferences) {
    return new InsuranceAddViewModel(insuranceRepository, vehicleRepository, brandRepository, reminderRepository, userPreferences);
  }
}
