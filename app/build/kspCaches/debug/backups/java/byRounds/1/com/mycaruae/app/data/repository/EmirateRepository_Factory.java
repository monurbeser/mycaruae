package com.mycaruae.app.data.repository;

import com.mycaruae.app.data.database.dao.EmirateDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class EmirateRepository_Factory implements Factory<EmirateRepository> {
  private final Provider<EmirateDao> emirateDaoProvider;

  public EmirateRepository_Factory(Provider<EmirateDao> emirateDaoProvider) {
    this.emirateDaoProvider = emirateDaoProvider;
  }

  @Override
  public EmirateRepository get() {
    return newInstance(emirateDaoProvider.get());
  }

  public static EmirateRepository_Factory create(Provider<EmirateDao> emirateDaoProvider) {
    return new EmirateRepository_Factory(emirateDaoProvider);
  }

  public static EmirateRepository newInstance(EmirateDao emirateDao) {
    return new EmirateRepository(emirateDao);
  }
}
