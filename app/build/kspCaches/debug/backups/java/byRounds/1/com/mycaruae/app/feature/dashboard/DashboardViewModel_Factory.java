package com.mycaruae.app.feature.dashboard;

import com.mycaruae.app.data.datastore.UserPreferences;
import com.mycaruae.app.data.repository.BrandRepository;
import com.mycaruae.app.data.repository.EmirateRepository;
import com.mycaruae.app.data.repository.MileageRepository;
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

  private final Provider<EmirateRepository> emirateRepositoryProvider;

  private final Provider<MileageRepository> mileageRepositoryProvider;

  private final Provider<UserPreferences> userPreferencesProvider;

  public DashboardViewModel_Factory(Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<BrandRepository> brandRepositoryProvider,
      Provider<EmirateRepository> emirateRepositoryProvider,
      Provider<MileageRepository> mileageRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    this.vehicleRepositoryProvider = vehicleRepositoryProvider;
    this.brandRepositoryProvider = brandRepositoryProvider;
    this.emirateRepositoryProvider = emirateRepositoryProvider;
    this.mileageRepositoryProvider = mileageRepositoryProvider;
    this.userPreferencesProvider = userPreferencesProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(vehicleRepositoryProvider.get(), brandRepositoryProvider.get(), emirateRepositoryProvider.get(), mileageRepositoryProvider.get(), userPreferencesProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<BrandRepository> brandRepositoryProvider,
      Provider<EmirateRepository> emirateRepositoryProvider,
      Provider<MileageRepository> mileageRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    return new DashboardViewModel_Factory(vehicleRepositoryProvider, brandRepositoryProvider, emirateRepositoryProvider, mileageRepositoryProvider, userPreferencesProvider);
  }

  public static DashboardViewModel newInstance(VehicleRepository vehicleRepository,
      BrandRepository brandRepository, EmirateRepository emirateRepository,
      MileageRepository mileageRepository, UserPreferences userPreferences) {
    return new DashboardViewModel(vehicleRepository, brandRepository, emirateRepository, mileageRepository, userPreferences);
  }
}
