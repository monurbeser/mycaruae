package com.mycaruae.app.di;

import com.mycaruae.app.data.database.CocDatabase;
import com.mycaruae.app.data.database.dao.InsuranceDao;
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
public final class DatabaseModule_ProvideInsuranceDaoFactory implements Factory<InsuranceDao> {
  private final Provider<CocDatabase> dbProvider;

  public DatabaseModule_ProvideInsuranceDaoFactory(Provider<CocDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public InsuranceDao get() {
    return provideInsuranceDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideInsuranceDaoFactory create(Provider<CocDatabase> dbProvider) {
    return new DatabaseModule_ProvideInsuranceDaoFactory(dbProvider);
  }

  public static InsuranceDao provideInsuranceDao(CocDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideInsuranceDao(db));
  }
}
