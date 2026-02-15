package com.mycaruae.app.di;

import com.mycaruae.app.data.database.CocDatabase;
import com.mycaruae.app.data.database.dao.EmirateDao;
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
public final class DatabaseModule_ProvideEmirateDaoFactory implements Factory<EmirateDao> {
  private final Provider<CocDatabase> dbProvider;

  public DatabaseModule_ProvideEmirateDaoFactory(Provider<CocDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public EmirateDao get() {
    return provideEmirateDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideEmirateDaoFactory create(Provider<CocDatabase> dbProvider) {
    return new DatabaseModule_ProvideEmirateDaoFactory(dbProvider);
  }

  public static EmirateDao provideEmirateDao(CocDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideEmirateDao(db));
  }
}
