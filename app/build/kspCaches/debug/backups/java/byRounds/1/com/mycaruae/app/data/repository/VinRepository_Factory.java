package com.mycaruae.app.data.repository;

import com.mycaruae.app.data.network.VinDecoderApi;
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
public final class VinRepository_Factory implements Factory<VinRepository> {
  private final Provider<VinDecoderApi> vinDecoderApiProvider;

  public VinRepository_Factory(Provider<VinDecoderApi> vinDecoderApiProvider) {
    this.vinDecoderApiProvider = vinDecoderApiProvider;
  }

  @Override
  public VinRepository get() {
    return newInstance(vinDecoderApiProvider.get());
  }

  public static VinRepository_Factory create(Provider<VinDecoderApi> vinDecoderApiProvider) {
    return new VinRepository_Factory(vinDecoderApiProvider);
  }

  public static VinRepository newInstance(VinDecoderApi vinDecoderApi) {
    return new VinRepository(vinDecoderApi);
  }
}
