package com.mycaruae.app.di;

import com.mycaruae.app.data.database.CocDatabase;
import com.mycaruae.app.data.database.dao.BrandDao;
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
public final class DatabaseModule_ProvideBrandDaoFactory implements Factory<BrandDao> {
  private final Provider<CocDatabase> dbProvider;

  public DatabaseModule_ProvideBrandDaoFactory(Provider<CocDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public BrandDao get() {
    return provideBrandDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideBrandDaoFactory create(Provider<CocDatabase> dbProvider) {
    return new DatabaseModule_ProvideBrandDaoFactory(dbProvider);
  }

  public static BrandDao provideBrandDao(CocDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideBrandDao(db));
  }
}
