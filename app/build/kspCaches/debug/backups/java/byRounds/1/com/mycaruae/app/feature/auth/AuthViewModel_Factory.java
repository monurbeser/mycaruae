package com.mycaruae.app.feature.auth;

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
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<UserPreferences> userPreferencesProvider;

  public AuthViewModel_Factory(Provider<UserPreferences> userPreferencesProvider) {
    this.userPreferencesProvider = userPreferencesProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(userPreferencesProvider.get());
  }

  public static AuthViewModel_Factory create(Provider<UserPreferences> userPreferencesProvider) {
    return new AuthViewModel_Factory(userPreferencesProvider);
  }

  public static AuthViewModel newInstance(UserPreferences userPreferences) {
    return new AuthViewModel(userPreferences);
  }
}
