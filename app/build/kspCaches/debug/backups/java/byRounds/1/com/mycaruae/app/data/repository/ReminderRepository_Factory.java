package com.mycaruae.app.data.repository;

import com.mycaruae.app.data.database.dao.ReminderDao;
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
public final class ReminderRepository_Factory implements Factory<ReminderRepository> {
  private final Provider<ReminderDao> reminderDaoProvider;

  public ReminderRepository_Factory(Provider<ReminderDao> reminderDaoProvider) {
    this.reminderDaoProvider = reminderDaoProvider;
  }

  @Override
  public ReminderRepository get() {
    return newInstance(reminderDaoProvider.get());
  }

  public static ReminderRepository_Factory create(Provider<ReminderDao> reminderDaoProvider) {
    return new ReminderRepository_Factory(reminderDaoProvider);
  }

  public static ReminderRepository newInstance(ReminderDao reminderDao) {
    return new ReminderRepository(reminderDao);
  }
}
