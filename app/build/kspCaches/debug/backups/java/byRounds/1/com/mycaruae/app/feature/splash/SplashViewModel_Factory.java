package com.mycaruae.app.feature.splash;

import com.mycaruae.app.data.datastore.UserPreferences;
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
public final class SplashViewModel_Factory implements Factory<SplashViewModel> {
  private final Provider<UserPreferences> userPreferencesProvider;

  public SplashViewModel_Factory(Provider<UserPreferences> userPreferencesProvider) {
    this.userPreferencesProvider = userPreferencesProvider;
  }

  @Override
  public SplashViewModel get() {
    return newInstance(userPreferencesProvider.get());
  }

  public static SplashViewModel_Factory create(Provider<UserPreferences> userPreferencesProvider) {
    return new SplashViewModel_Factory(userPreferencesProvider);
  }

  public static SplashViewModel newInstance(UserPreferences userPreferences) {
    return new SplashViewModel(userPreferences);
  }
}
