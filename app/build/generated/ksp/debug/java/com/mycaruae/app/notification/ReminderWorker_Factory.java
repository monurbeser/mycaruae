package com.mycaruae.app.notification;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.mycaruae.app.data.datastore.UserPreferences;
import com.mycaruae.app.data.repository.ReminderRepository;
import com.mycaruae.app.data.repository.VehicleRepository;
import dagger.internal.DaggerGenerated;
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
public final class ReminderWorker_Factory {
  private final Provider<VehicleRepository> vehicleRepositoryProvider;

  private final Provider<ReminderRepository> reminderRepositoryProvider;

  private final Provider<UserPreferences> userPreferencesProvider;

  private final Provider<NotificationHelper> notificationHelperProvider;

  public ReminderWorker_Factory(Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<ReminderRepository> reminderRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider,
      Provider<NotificationHelper> notificationHelperProvider) {
    this.vehicleRepositoryProvider = vehicleRepositoryProvider;
    this.reminderRepositoryProvider = reminderRepositoryProvider;
    this.userPreferencesProvider = userPreferencesProvider;
    this.notificationHelperProvider = notificationHelperProvider;
  }

  public ReminderWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, vehicleRepositoryProvider.get(), reminderRepositoryProvider.get(), userPreferencesProvider.get(), notificationHelperProvider.get());
  }

  public static ReminderWorker_Factory create(Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<ReminderRepository> reminderRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider,
      Provider<NotificationHelper> notificationHelperProvider) {
    return new ReminderWorker_Factory(vehicleRepositoryProvider, reminderRepositoryProvider, userPreferencesProvider, notificationHelperProvider);
  }

  public static ReminderWorker newInstance(Context appContext, WorkerParameters workerParams,
      VehicleRepository vehicleRepository, ReminderRepository reminderRepository,
      UserPreferences userPreferences, NotificationHelper notificationHelper) {
    return new ReminderWorker(appContext, workerParams, vehicleRepository, reminderRepository, userPreferences, notificationHelper);
  }
}
