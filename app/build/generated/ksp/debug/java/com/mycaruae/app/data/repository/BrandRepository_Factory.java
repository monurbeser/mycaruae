package com.mycaruae.app.data.repository;

import com.mycaruae.app.data.database.dao.BrandDao;
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
public final class BrandRepository_Factory implements Factory<BrandRepository> {
  private final Provider<BrandDao> brandDaoProvider;

  public BrandRepository_Factory(Provider<BrandDao> brandDaoProvider) {
    this.brandDaoProvider = brandDaoProvider;
  }

  @Override
  public BrandRepository get() {
    return newInstance(brandDaoProvider.get());
  }

  public static BrandRepository_Factory create(Provider<BrandDao> brandDaoProvider) {
    return new BrandRepository_Factory(brandDaoProvider);
  }

  public static BrandRepository newInstance(BrandDao brandDao) {
    return new BrandRepository(brandDao);
  }
}
