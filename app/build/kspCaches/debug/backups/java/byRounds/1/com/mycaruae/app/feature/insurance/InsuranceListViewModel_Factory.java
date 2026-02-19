package com.mycaruae.app.feature.insurance;

import com.mycaruae.app.data.datastore.UserPreferences;
import com.mycaruae.app.data.repository.BrandRepository;
import com.mycaruae.app.data.repository.InsuranceRepository;
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
public final class InsuranceListViewModel_Factory implements Factory<InsuranceListViewModel> {
  private final Provider<InsuranceRepository> insuranceRepositoryProvider;

  private final Provider<VehicleRepository> vehicleRepositoryProvider;

  private final Provider<BrandRepository> brandRepositoryProvider;

  private final Provider<UserPreferences> userPreferencesProvider;

  public InsuranceListViewModel_Factory(Provider<InsuranceRepository> insuranceRepositoryProvider,
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<BrandRepository> brandRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    this.insuranceRepositoryProvider = insuranceRepositoryProvider;
    this.vehicleRepositoryProvider = vehicleRepositoryProvider;
    this.brandRepositoryProvider = brandRepositoryProvider;
    this.userPreferencesProvider = userPreferencesProvider;
  }

  @Override
  public InsuranceListViewModel get() {
    return newInstance(insuranceRepositoryProvider.get(), vehicleRepositoryProvider.get(), brandRepositoryProvider.get(), userPreferencesProvider.get());
  }

  public static InsuranceListViewModel_Factory create(
      Provider<InsuranceRepository> insuranceRepositoryProvider,
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<BrandRepository> brandRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    return new InsuranceListViewModel_Factory(insuranceRepositoryProvider, vehicleRepositoryProvider, brandRepositoryProvider, userPreferencesProvider);
  }

  public static InsuranceListViewModel newInstance(InsuranceRepository insuranceRepository,
      VehicleRepository vehicleRepository, BrandRepository brandRepository,
      UserPreferences userPreferences) {
    return new InsuranceListViewModel(insuranceRepository, vehicleRepository, brandRepository, userPreferences);
  }
}
