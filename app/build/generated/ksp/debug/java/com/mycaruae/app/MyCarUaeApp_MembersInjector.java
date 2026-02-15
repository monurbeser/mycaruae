package com.mycaruae.app;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MyCarUaeApp_MembersInjector implements MembersInjector<MyCarUaeApp> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public MyCarUaeApp_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<MyCarUaeApp> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new MyCarUaeApp_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(MyCarUaeApp instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.mycaruae.app.MyCarUaeApp.workerFactory")
  public static void injectWorkerFactory(MyCarUaeApp instance, HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
