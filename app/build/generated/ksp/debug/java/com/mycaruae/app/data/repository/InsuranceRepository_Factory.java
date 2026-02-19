package com.mycaruae.app.data.repository;

import com.mycaruae.app.data.database.dao.InsuranceDao;
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
public final class InsuranceRepository_Factory implements Factory<InsuranceRepository> {
  private final Provider<InsuranceDao> insuranceDaoProvider;

  public InsuranceRepository_Factory(Provider<InsuranceDao> insuranceDaoProvider) {
    this.insuranceDaoProvider = insuranceDaoProvider;
  }

  @Override
  public InsuranceRepository get() {
    return newInstance(insuranceDaoProvider.get());
  }

  public static InsuranceRepository_Factory create(Provider<InsuranceDao> insuranceDaoProvider) {
    return new InsuranceRepository_Factory(insuranceDaoProvider);
  }

  public static InsuranceRepository newInstance(InsuranceDao insuranceDao) {
    return new InsuranceRepository(insuranceDao);
  }
}
