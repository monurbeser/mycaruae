package com.mycaruae.app.di;

import com.mycaruae.app.data.network.VinDecoderApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class NetworkModule_ProvideVinDecoderApiFactory implements Factory<VinDecoderApi> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideVinDecoderApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public VinDecoderApi get() {
    return provideVinDecoderApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideVinDecoderApiFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideVinDecoderApiFactory(retrofitProvider);
  }

  public static VinDecoderApi provideVinDecoderApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideVinDecoderApi(retrofit));
  }
}
