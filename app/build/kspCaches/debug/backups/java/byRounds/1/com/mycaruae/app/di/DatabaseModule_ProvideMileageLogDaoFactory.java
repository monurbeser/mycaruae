package com.mycaruae.app.di;

import com.mycaruae.app.data.database.CocDatabase;
import com.mycaruae.app.data.database.dao.MileageLogDao;
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
public final class DatabaseModule_ProvideMileageLogDaoFactory implements Factory<MileageLogDao> {
  private final Provider<CocDatabase> dbProvider;

  public DatabaseModule_ProvideMileageLogDaoFactory(Provider<CocDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public MileageLogDao get() {
    return provideMileageLogDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideMileageLogDaoFactory create(
      Provider<CocDatabase> dbProvider) {
    return new DatabaseModule_ProvideMileageLogDaoFactory(dbProvider);
  }

  public static MileageLogDao provideMileageLogDao(CocDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideMileageLogDao(db));
  }
}
