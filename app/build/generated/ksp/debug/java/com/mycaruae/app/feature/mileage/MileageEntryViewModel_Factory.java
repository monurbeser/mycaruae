package com.mycaruae.app.feature.mileage;

import com.mycaruae.app.data.datastore.UserPreferences;
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
public final class MileageEntryViewModel_Factory implements Factory<MileageEntryViewModel> {
  private final Provider<MileageRepository> mileageRepositoryProvider;

  private final Provider<VehicleRepository> vehicleRepositoryProvider;

  private final Provider<UserPreferences> userPreferencesProvider;

  public MileageEntryViewModel_Factory(Provider<MileageRepository> mileageRepositoryProvider,
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    this.mileageRepositoryProvider = mileageRepositoryProvider;
    this.vehicleRepositoryProvider = vehicleRepositoryProvider;
    this.userPreferencesProvider = userPreferencesProvider;
  }

  @Override
  public MileageEntryViewModel get() {
    return newInstance(mileageRepositoryProvider.get(), vehicleRepositoryProvider.get(), userPreferencesProvider.get());
  }

  public static MileageEntryViewModel_Factory create(
      Provider<MileageRepository> mileageRepositoryProvider,
      Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    return new MileageEntryViewModel_Factory(mileageRepositoryProvider, vehicleRepositoryProvider, userPreferencesProvider);
  }

  public static MileageEntryViewModel newInstance(MileageRepository mileageRepository,
      VehicleRepository vehicleRepository, UserPreferences userPreferences) {
    return new MileageEntryViewModel(mileageRepository, vehicleRepository, userPreferences);
  }
}
