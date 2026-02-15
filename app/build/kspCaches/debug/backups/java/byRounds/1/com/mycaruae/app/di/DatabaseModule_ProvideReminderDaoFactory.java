package com.mycaruae.app.di;

import com.mycaruae.app.data.database.CocDatabase;
import com.mycaruae.app.data.database.dao.ReminderDao;
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
public final class DatabaseModule_ProvideReminderDaoFactory implements Factory<ReminderDao> {
  private final Provider<CocDatabase> dbProvider;

  public DatabaseModule_ProvideReminderDaoFactory(Provider<CocDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ReminderDao get() {
    return provideReminderDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideReminderDaoFactory create(Provider<CocDatabase> dbProvider) {
    return new DatabaseModule_ProvideReminderDaoFactory(dbProvider);
  }

  public static ReminderDao provideReminderDao(CocDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideReminderDao(db));
  }
}
