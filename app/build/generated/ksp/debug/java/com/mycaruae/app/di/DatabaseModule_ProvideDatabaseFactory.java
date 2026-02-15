package com.mycaruae.app.di;

import android.content.Context;
import com.mycaruae.app.data.database.CocDatabase;
import com.mycaruae.app.data.database.dao.BrandDao;
import com.mycaruae.app.data.database.dao.EmirateDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DatabaseModule_ProvideDatabaseFactory implements Factory<CocDatabase> {
  private final Provider<Context> contextProvider;

  private final Provider<EmirateDao> emirateDaoProvider;

  private final Provider<BrandDao> brandDaoProvider;

  public DatabaseModule_ProvideDatabaseFactory(Provider<Context> contextProvider,
      Provider<EmirateDao> emirateDaoProvider, Provider<BrandDao> brandDaoProvider) {
    this.contextProvider = contextProvider;
    this.emirateDaoProvider = emirateDaoProvider;
    this.brandDaoProvider = brandDaoProvider;
  }

  @Override
  public CocDatabase get() {
    return provideDatabase(contextProvider.get(), emirateDaoProvider, brandDaoProvider);
  }

  public static DatabaseModule_ProvideDatabaseFactory create(Provider<Context> contextProvider,
      Provider<EmirateDao> emirateDaoProvider, Provider<BrandDao> brandDaoProvider) {
    return new DatabaseModule_ProvideDatabaseFactory(contextProvider, emirateDaoProvider, brandDaoProvider);
  }

  public static CocDatabase provideDatabase(Context context, Provider<EmirateDao> emirateDao,
      Provider<BrandDao> brandDao) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideDatabase(context, emirateDao, brandDao));
  }
}
