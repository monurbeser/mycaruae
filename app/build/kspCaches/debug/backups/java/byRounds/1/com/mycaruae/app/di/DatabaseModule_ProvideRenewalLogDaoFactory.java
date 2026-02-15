package com.mycaruae.app.di;

import com.mycaruae.app.data.database.CocDatabase;
import com.mycaruae.app.data.database.dao.RenewalLogDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideRenewalLogDaoFactory implements Factory<RenewalLogDao> {
  private final Provider<CocDatabase> dbProvider;

  public DatabaseModule_ProvideRenewalLogDaoFactory(Provider<CocDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public RenewalLogDao get() {
    return provideRenewalLogDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideRenewalLogDaoFactory create(
      Provider<CocDatabase> dbProvider) {
    return new DatabaseModule_ProvideRenewalLogDaoFactory(dbProvider);
  }

  public static RenewalLogDao provideRenewalLogDao(CocDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideRenewalLogDao(db));
  }
}
