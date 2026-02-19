package com.mycaruae.app;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.mycaruae.app.data.database.CocDatabase;
import com.mycaruae.app.data.database.dao.BrandDao;
import com.mycaruae.app.data.database.dao.EmirateDao;
import com.mycaruae.app.data.database.dao.InsuranceDao;
import com.mycaruae.app.data.database.dao.MaintenanceDao;
import com.mycaruae.app.data.database.dao.MileageLogDao;
import com.mycaruae.app.data.database.dao.ReminderDao;
import com.mycaruae.app.data.database.dao.VehicleDao;
import com.mycaruae.app.data.datastore.UserPreferences;
import com.mycaruae.app.data.repository.BrandRepository;
import com.mycaruae.app.data.repository.EmirateRepository;
import com.mycaruae.app.data.repository.InsuranceRepository;
import com.mycaruae.app.data.repository.MaintenanceRepository;
import com.mycaruae.app.data.repository.MileageRepository;
import com.mycaruae.app.data.repository.ReminderRepository;
import com.mycaruae.app.data.repository.VehicleRepository;
import com.mycaruae.app.di.DataStoreModule_ProvideDataStoreFactory;
import com.mycaruae.app.di.DatabaseModule_ProvideBrandDaoFactory;
import com.mycaruae.app.di.DatabaseModule_ProvideDatabaseFactory;
import com.mycaruae.app.di.DatabaseModule_ProvideEmirateDaoFactory;
import com.mycaruae.app.di.DatabaseModule_ProvideInsuranceDaoFactory;
import com.mycaruae.app.di.DatabaseModule_ProvideMaintenanceDaoFactory;
import com.mycaruae.app.di.DatabaseModule_ProvideMileageLogDaoFactory;
import com.mycaruae.app.di.DatabaseModule_ProvideReminderDaoFactory;
import com.mycaruae.app.di.DatabaseModule_ProvideVehicleDaoFactory;
import com.mycaruae.app.feature.auth.AuthViewModel;
import com.mycaruae.app.feature.auth.AuthViewModel_HiltModules;
import com.mycaruae.app.feature.dashboard.DashboardViewModel;
import com.mycaruae.app.feature.dashboard.DashboardViewModel_HiltModules;
import com.mycaruae.app.feature.insurance.InsuranceAddViewModel;
import com.mycaruae.app.feature.insurance.InsuranceAddViewModel_HiltModules;
import com.mycaruae.app.feature.insurance.InsuranceListViewModel;
import com.mycaruae.app.feature.insurance.InsuranceListViewModel_HiltModules;
import com.mycaruae.app.feature.maintenance.MaintenanceAddViewModel;
import com.mycaruae.app.feature.maintenance.MaintenanceAddViewModel_HiltModules;
import com.mycaruae.app.feature.maintenance.MaintenanceDetailViewModel;
import com.mycaruae.app.feature.maintenance.MaintenanceDetailViewModel_HiltModules;
import com.mycaruae.app.feature.maintenance.MaintenanceHistoryViewModel;
import com.mycaruae.app.feature.maintenance.MaintenanceHistoryViewModel_HiltModules;
import com.mycaruae.app.feature.mileage.MileageEntryViewModel;
import com.mycaruae.app.feature.mileage.MileageEntryViewModel_HiltModules;
import com.mycaruae.app.feature.mileage.MileageHistoryViewModel;
import com.mycaruae.app.feature.mileage.MileageHistoryViewModel_HiltModules;
import com.mycaruae.app.feature.reminders.ReminderCreateViewModel;
import com.mycaruae.app.feature.reminders.ReminderCreateViewModel_HiltModules;
import com.mycaruae.app.feature.reminders.RemindersViewModel;
import com.mycaruae.app.feature.reminders.RemindersViewModel_HiltModules;
import com.mycaruae.app.feature.settings.SettingsViewModel;
import com.mycaruae.app.feature.settings.SettingsViewModel_HiltModules;
import com.mycaruae.app.feature.splash.SplashViewModel;
import com.mycaruae.app.feature.splash.SplashViewModel_HiltModules;
import com.mycaruae.app.feature.vehicle.VehicleAddViewModel;
import com.mycaruae.app.feature.vehicle.VehicleAddViewModel_HiltModules;
import com.mycaruae.app.feature.vehicle.VehicleEditViewModel;
import com.mycaruae.app.feature.vehicle.VehicleEditViewModel_HiltModules;
import com.mycaruae.app.notification.NotificationHelper;
import com.mycaruae.app.notification.ReminderWorker;
import com.mycaruae.app.notification.ReminderWorker_AssistedFactory;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DelegateFactory;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerMyCarUaeApp_HiltComponents_SingletonC {
  private DaggerMyCarUaeApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public MyCarUaeApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements MyCarUaeApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public MyCarUaeApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements MyCarUaeApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public MyCarUaeApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements MyCarUaeApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public MyCarUaeApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements MyCarUaeApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public MyCarUaeApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements MyCarUaeApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public MyCarUaeApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements MyCarUaeApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public MyCarUaeApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements MyCarUaeApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public MyCarUaeApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends MyCarUaeApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends MyCarUaeApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends MyCarUaeApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends MyCarUaeApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(15).put(LazyClassKeyProvider.com_mycaruae_app_feature_auth_AuthViewModel, AuthViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_mycaruae_app_feature_dashboard_DashboardViewModel, DashboardViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_mycaruae_app_feature_insurance_InsuranceAddViewModel, InsuranceAddViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_mycaruae_app_feature_insurance_InsuranceListViewModel, InsuranceListViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_mycaruae_app_feature_maintenance_MaintenanceAddViewModel, MaintenanceAddViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_mycaruae_app_feature_maintenance_MaintenanceDetailViewModel, MaintenanceDetailViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_mycaruae_app_feature_maintenance_MaintenanceHistoryViewModel, MaintenanceHistoryViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_mycaruae_app_feature_mileage_MileageEntryViewModel, MileageEntryViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_mycaruae_app_feature_mileage_MileageHistoryViewModel, MileageHistoryViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_mycaruae_app_feature_reminders_ReminderCreateViewModel, ReminderCreateViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_mycaruae_app_feature_reminders_RemindersViewModel, RemindersViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_mycaruae_app_feature_settings_SettingsViewModel, SettingsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_mycaruae_app_feature_splash_SplashViewModel, SplashViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_mycaruae_app_feature_vehicle_VehicleAddViewModel, VehicleAddViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_mycaruae_app_feature_vehicle_VehicleEditViewModel, VehicleEditViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_mycaruae_app_feature_maintenance_MaintenanceAddViewModel = "com.mycaruae.app.feature.maintenance.MaintenanceAddViewModel";

      static String com_mycaruae_app_feature_mileage_MileageEntryViewModel = "com.mycaruae.app.feature.mileage.MileageEntryViewModel";

      static String com_mycaruae_app_feature_vehicle_VehicleEditViewModel = "com.mycaruae.app.feature.vehicle.VehicleEditViewModel";

      static String com_mycaruae_app_feature_dashboard_DashboardViewModel = "com.mycaruae.app.feature.dashboard.DashboardViewModel";

      static String com_mycaruae_app_feature_splash_SplashViewModel = "com.mycaruae.app.feature.splash.SplashViewModel";

      static String com_mycaruae_app_feature_reminders_ReminderCreateViewModel = "com.mycaruae.app.feature.reminders.ReminderCreateViewModel";

      static String com_mycaruae_app_feature_insurance_InsuranceListViewModel = "com.mycaruae.app.feature.insurance.InsuranceListViewModel";

      static String com_mycaruae_app_feature_maintenance_MaintenanceHistoryViewModel = "com.mycaruae.app.feature.maintenance.MaintenanceHistoryViewModel";

      static String com_mycaruae_app_feature_mileage_MileageHistoryViewModel = "com.mycaruae.app.feature.mileage.MileageHistoryViewModel";

      static String com_mycaruae_app_feature_vehicle_VehicleAddViewModel = "com.mycaruae.app.feature.vehicle.VehicleAddViewModel";

      static String com_mycaruae_app_feature_insurance_InsuranceAddViewModel = "com.mycaruae.app.feature.insurance.InsuranceAddViewModel";

      static String com_mycaruae_app_feature_settings_SettingsViewModel = "com.mycaruae.app.feature.settings.SettingsViewModel";

      static String com_mycaruae_app_feature_reminders_RemindersViewModel = "com.mycaruae.app.feature.reminders.RemindersViewModel";

      static String com_mycaruae_app_feature_auth_AuthViewModel = "com.mycaruae.app.feature.auth.AuthViewModel";

      static String com_mycaruae_app_feature_maintenance_MaintenanceDetailViewModel = "com.mycaruae.app.feature.maintenance.MaintenanceDetailViewModel";

      @KeepFieldType
      MaintenanceAddViewModel com_mycaruae_app_feature_maintenance_MaintenanceAddViewModel2;

      @KeepFieldType
      MileageEntryViewModel com_mycaruae_app_feature_mileage_MileageEntryViewModel2;

      @KeepFieldType
      VehicleEditViewModel com_mycaruae_app_feature_vehicle_VehicleEditViewModel2;

      @KeepFieldType
      DashboardViewModel com_mycaruae_app_feature_dashboard_DashboardViewModel2;

      @KeepFieldType
      SplashViewModel com_mycaruae_app_feature_splash_SplashViewModel2;

      @KeepFieldType
      ReminderCreateViewModel com_mycaruae_app_feature_reminders_ReminderCreateViewModel2;

      @KeepFieldType
      InsuranceListViewModel com_mycaruae_app_feature_insurance_InsuranceListViewModel2;

      @KeepFieldType
      MaintenanceHistoryViewModel com_mycaruae_app_feature_maintenance_MaintenanceHistoryViewModel2;

      @KeepFieldType
      MileageHistoryViewModel com_mycaruae_app_feature_mileage_MileageHistoryViewModel2;

      @KeepFieldType
      VehicleAddViewModel com_mycaruae_app_feature_vehicle_VehicleAddViewModel2;

      @KeepFieldType
      InsuranceAddViewModel com_mycaruae_app_feature_insurance_InsuranceAddViewModel2;

      @KeepFieldType
      SettingsViewModel com_mycaruae_app_feature_settings_SettingsViewModel2;

      @KeepFieldType
      RemindersViewModel com_mycaruae_app_feature_reminders_RemindersViewModel2;

      @KeepFieldType
      AuthViewModel com_mycaruae_app_feature_auth_AuthViewModel2;

      @KeepFieldType
      MaintenanceDetailViewModel com_mycaruae_app_feature_maintenance_MaintenanceDetailViewModel2;
    }
  }

  private static final class ViewModelCImpl extends MyCarUaeApp_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AuthViewModel> authViewModelProvider;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<InsuranceAddViewModel> insuranceAddViewModelProvider;

    private Provider<InsuranceListViewModel> insuranceListViewModelProvider;

    private Provider<MaintenanceAddViewModel> maintenanceAddViewModelProvider;

    private Provider<MaintenanceDetailViewModel> maintenanceDetailViewModelProvider;

    private Provider<MaintenanceHistoryViewModel> maintenanceHistoryViewModelProvider;

    private Provider<MileageEntryViewModel> mileageEntryViewModelProvider;

    private Provider<MileageHistoryViewModel> mileageHistoryViewModelProvider;

    private Provider<ReminderCreateViewModel> reminderCreateViewModelProvider;

    private Provider<RemindersViewModel> remindersViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private Provider<SplashViewModel> splashViewModelProvider;

    private Provider<VehicleAddViewModel> vehicleAddViewModelProvider;

    private Provider<VehicleEditViewModel> vehicleEditViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.authViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.insuranceAddViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.insuranceListViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.maintenanceAddViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.maintenanceDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.maintenanceHistoryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.mileageEntryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.mileageHistoryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.reminderCreateViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.remindersViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
      this.splashViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 12);
      this.vehicleAddViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 13);
      this.vehicleEditViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 14);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(15).put(LazyClassKeyProvider.com_mycaruae_app_feature_auth_AuthViewModel, ((Provider) authViewModelProvider)).put(LazyClassKeyProvider.com_mycaruae_app_feature_dashboard_DashboardViewModel, ((Provider) dashboardViewModelProvider)).put(LazyClassKeyProvider.com_mycaruae_app_feature_insurance_InsuranceAddViewModel, ((Provider) insuranceAddViewModelProvider)).put(LazyClassKeyProvider.com_mycaruae_app_feature_insurance_InsuranceListViewModel, ((Provider) insuranceListViewModelProvider)).put(LazyClassKeyProvider.com_mycaruae_app_feature_maintenance_MaintenanceAddViewModel, ((Provider) maintenanceAddViewModelProvider)).put(LazyClassKeyProvider.com_mycaruae_app_feature_maintenance_MaintenanceDetailViewModel, ((Provider) maintenanceDetailViewModelProvider)).put(LazyClassKeyProvider.com_mycaruae_app_feature_maintenance_MaintenanceHistoryViewModel, ((Provider) maintenanceHistoryViewModelProvider)).put(LazyClassKeyProvider.com_mycaruae_app_feature_mileage_MileageEntryViewModel, ((Provider) mileageEntryViewModelProvider)).put(LazyClassKeyProvider.com_mycaruae_app_feature_mileage_MileageHistoryViewModel, ((Provider) mileageHistoryViewModelProvider)).put(LazyClassKeyProvider.com_mycaruae_app_feature_reminders_ReminderCreateViewModel, ((Provider) reminderCreateViewModelProvider)).put(LazyClassKeyProvider.com_mycaruae_app_feature_reminders_RemindersViewModel, ((Provider) remindersViewModelProvider)).put(LazyClassKeyProvider.com_mycaruae_app_feature_settings_SettingsViewModel, ((Provider) settingsViewModelProvider)).put(LazyClassKeyProvider.com_mycaruae_app_feature_splash_SplashViewModel, ((Provider) splashViewModelProvider)).put(LazyClassKeyProvider.com_mycaruae_app_feature_vehicle_VehicleAddViewModel, ((Provider) vehicleAddViewModelProvider)).put(LazyClassKeyProvider.com_mycaruae_app_feature_vehicle_VehicleEditViewModel, ((Provider) vehicleEditViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_mycaruae_app_feature_maintenance_MaintenanceDetailViewModel = "com.mycaruae.app.feature.maintenance.MaintenanceDetailViewModel";

      static String com_mycaruae_app_feature_maintenance_MaintenanceHistoryViewModel = "com.mycaruae.app.feature.maintenance.MaintenanceHistoryViewModel";

      static String com_mycaruae_app_feature_insurance_InsuranceAddViewModel = "com.mycaruae.app.feature.insurance.InsuranceAddViewModel";

      static String com_mycaruae_app_feature_vehicle_VehicleEditViewModel = "com.mycaruae.app.feature.vehicle.VehicleEditViewModel";

      static String com_mycaruae_app_feature_reminders_RemindersViewModel = "com.mycaruae.app.feature.reminders.RemindersViewModel";

      static String com_mycaruae_app_feature_mileage_MileageHistoryViewModel = "com.mycaruae.app.feature.mileage.MileageHistoryViewModel";

      static String com_mycaruae_app_feature_insurance_InsuranceListViewModel = "com.mycaruae.app.feature.insurance.InsuranceListViewModel";

      static String com_mycaruae_app_feature_auth_AuthViewModel = "com.mycaruae.app.feature.auth.AuthViewModel";

      static String com_mycaruae_app_feature_dashboard_DashboardViewModel = "com.mycaruae.app.feature.dashboard.DashboardViewModel";

      static String com_mycaruae_app_feature_vehicle_VehicleAddViewModel = "com.mycaruae.app.feature.vehicle.VehicleAddViewModel";

      static String com_mycaruae_app_feature_maintenance_MaintenanceAddViewModel = "com.mycaruae.app.feature.maintenance.MaintenanceAddViewModel";

      static String com_mycaruae_app_feature_mileage_MileageEntryViewModel = "com.mycaruae.app.feature.mileage.MileageEntryViewModel";

      static String com_mycaruae_app_feature_settings_SettingsViewModel = "com.mycaruae.app.feature.settings.SettingsViewModel";

      static String com_mycaruae_app_feature_reminders_ReminderCreateViewModel = "com.mycaruae.app.feature.reminders.ReminderCreateViewModel";

      static String com_mycaruae_app_feature_splash_SplashViewModel = "com.mycaruae.app.feature.splash.SplashViewModel";

      @KeepFieldType
      MaintenanceDetailViewModel com_mycaruae_app_feature_maintenance_MaintenanceDetailViewModel2;

      @KeepFieldType
      MaintenanceHistoryViewModel com_mycaruae_app_feature_maintenance_MaintenanceHistoryViewModel2;

      @KeepFieldType
      InsuranceAddViewModel com_mycaruae_app_feature_insurance_InsuranceAddViewModel2;

      @KeepFieldType
      VehicleEditViewModel com_mycaruae_app_feature_vehicle_VehicleEditViewModel2;

      @KeepFieldType
      RemindersViewModel com_mycaruae_app_feature_reminders_RemindersViewModel2;

      @KeepFieldType
      MileageHistoryViewModel com_mycaruae_app_feature_mileage_MileageHistoryViewModel2;

      @KeepFieldType
      InsuranceListViewModel com_mycaruae_app_feature_insurance_InsuranceListViewModel2;

      @KeepFieldType
      AuthViewModel com_mycaruae_app_feature_auth_AuthViewModel2;

      @KeepFieldType
      DashboardViewModel com_mycaruae_app_feature_dashboard_DashboardViewModel2;

      @KeepFieldType
      VehicleAddViewModel com_mycaruae_app_feature_vehicle_VehicleAddViewModel2;

      @KeepFieldType
      MaintenanceAddViewModel com_mycaruae_app_feature_maintenance_MaintenanceAddViewModel2;

      @KeepFieldType
      MileageEntryViewModel com_mycaruae_app_feature_mileage_MileageEntryViewModel2;

      @KeepFieldType
      SettingsViewModel com_mycaruae_app_feature_settings_SettingsViewModel2;

      @KeepFieldType
      ReminderCreateViewModel com_mycaruae_app_feature_reminders_ReminderCreateViewModel2;

      @KeepFieldType
      SplashViewModel com_mycaruae_app_feature_splash_SplashViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.mycaruae.app.feature.auth.AuthViewModel 
          return (T) new AuthViewModel(singletonCImpl.userPreferencesProvider.get());

          case 1: // com.mycaruae.app.feature.dashboard.DashboardViewModel 
          return (T) new DashboardViewModel(singletonCImpl.vehicleRepositoryProvider.get(), singletonCImpl.brandRepositoryProvider.get(), singletonCImpl.emirateRepositoryProvider.get(), singletonCImpl.mileageRepositoryProvider.get(), singletonCImpl.userPreferencesProvider.get());

          case 2: // com.mycaruae.app.feature.insurance.InsuranceAddViewModel 
          return (T) new InsuranceAddViewModel(singletonCImpl.insuranceRepositoryProvider.get(), singletonCImpl.vehicleRepositoryProvider.get(), singletonCImpl.brandRepositoryProvider.get(), singletonCImpl.reminderRepositoryProvider.get(), singletonCImpl.userPreferencesProvider.get());

          case 3: // com.mycaruae.app.feature.insurance.InsuranceListViewModel 
          return (T) new InsuranceListViewModel(singletonCImpl.insuranceRepositoryProvider.get(), singletonCImpl.vehicleRepositoryProvider.get(), singletonCImpl.brandRepositoryProvider.get(), singletonCImpl.userPreferencesProvider.get());

          case 4: // com.mycaruae.app.feature.maintenance.MaintenanceAddViewModel 
          return (T) new MaintenanceAddViewModel(singletonCImpl.maintenanceRepositoryProvider.get(), singletonCImpl.vehicleRepositoryProvider.get(), singletonCImpl.userPreferencesProvider.get());

          case 5: // com.mycaruae.app.feature.maintenance.MaintenanceDetailViewModel 
          return (T) new MaintenanceDetailViewModel(viewModelCImpl.savedStateHandle, singletonCImpl.maintenanceRepositoryProvider.get(), singletonCImpl.vehicleRepositoryProvider.get(), singletonCImpl.userPreferencesProvider.get());

          case 6: // com.mycaruae.app.feature.maintenance.MaintenanceHistoryViewModel 
          return (T) new MaintenanceHistoryViewModel(singletonCImpl.maintenanceRepositoryProvider.get(), singletonCImpl.vehicleRepositoryProvider.get(), singletonCImpl.userPreferencesProvider.get());

          case 7: // com.mycaruae.app.feature.mileage.MileageEntryViewModel 
          return (T) new MileageEntryViewModel(singletonCImpl.mileageRepositoryProvider.get(), singletonCImpl.vehicleRepositoryProvider.get(), singletonCImpl.userPreferencesProvider.get());

          case 8: // com.mycaruae.app.feature.mileage.MileageHistoryViewModel 
          return (T) new MileageHistoryViewModel(singletonCImpl.mileageRepositoryProvider.get(), singletonCImpl.vehicleRepositoryProvider.get(), singletonCImpl.userPreferencesProvider.get());

          case 9: // com.mycaruae.app.feature.reminders.ReminderCreateViewModel 
          return (T) new ReminderCreateViewModel(singletonCImpl.reminderRepositoryProvider.get(), singletonCImpl.vehicleRepositoryProvider.get(), singletonCImpl.userPreferencesProvider.get());

          case 10: // com.mycaruae.app.feature.reminders.RemindersViewModel 
          return (T) new RemindersViewModel(singletonCImpl.reminderRepositoryProvider.get(), singletonCImpl.vehicleRepositoryProvider.get(), singletonCImpl.brandRepositoryProvider.get(), singletonCImpl.userPreferencesProvider.get());

          case 11: // com.mycaruae.app.feature.settings.SettingsViewModel 
          return (T) new SettingsViewModel(singletonCImpl.userPreferencesProvider.get(), singletonCImpl.vehicleRepositoryProvider.get(), singletonCImpl.reminderRepositoryProvider.get(), singletonCImpl.notificationHelperProvider.get());

          case 12: // com.mycaruae.app.feature.splash.SplashViewModel 
          return (T) new SplashViewModel(singletonCImpl.userPreferencesProvider.get());

          case 13: // com.mycaruae.app.feature.vehicle.VehicleAddViewModel 
          return (T) new VehicleAddViewModel(singletonCImpl.vehicleRepositoryProvider.get(), singletonCImpl.brandRepositoryProvider.get(), singletonCImpl.emirateRepositoryProvider.get(), singletonCImpl.reminderRepositoryProvider.get(), singletonCImpl.userPreferencesProvider.get());

          case 14: // com.mycaruae.app.feature.vehicle.VehicleEditViewModel 
          return (T) new VehicleEditViewModel(singletonCImpl.vehicleRepositoryProvider.get(), singletonCImpl.brandRepositoryProvider.get(), singletonCImpl.emirateRepositoryProvider.get(), singletonCImpl.reminderRepositoryProvider.get(), singletonCImpl.userPreferencesProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends MyCarUaeApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends MyCarUaeApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends MyCarUaeApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<CocDatabase> provideDatabaseProvider;

    private Provider<EmirateDao> provideEmirateDaoProvider;

    private Provider<BrandDao> provideBrandDaoProvider;

    private Provider<VehicleRepository> vehicleRepositoryProvider;

    private Provider<ReminderRepository> reminderRepositoryProvider;

    private Provider<DataStore<Preferences>> provideDataStoreProvider;

    private Provider<UserPreferences> userPreferencesProvider;

    private Provider<NotificationHelper> notificationHelperProvider;

    private Provider<ReminderWorker_AssistedFactory> reminderWorker_AssistedFactoryProvider;

    private Provider<BrandRepository> brandRepositoryProvider;

    private Provider<EmirateRepository> emirateRepositoryProvider;

    private Provider<MileageRepository> mileageRepositoryProvider;

    private Provider<InsuranceRepository> insuranceRepositoryProvider;

    private Provider<MaintenanceRepository> maintenanceRepositoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private VehicleDao vehicleDao() {
      return DatabaseModule_ProvideVehicleDaoFactory.provideVehicleDao(provideDatabaseProvider.get());
    }

    private ReminderDao reminderDao() {
      return DatabaseModule_ProvideReminderDaoFactory.provideReminderDao(provideDatabaseProvider.get());
    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return Collections.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>singletonMap("com.mycaruae.app.notification.ReminderWorker", ((Provider) reminderWorker_AssistedFactoryProvider));
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    private MileageLogDao mileageLogDao() {
      return DatabaseModule_ProvideMileageLogDaoFactory.provideMileageLogDao(provideDatabaseProvider.get());
    }

    private InsuranceDao insuranceDao() {
      return DatabaseModule_ProvideInsuranceDaoFactory.provideInsuranceDao(provideDatabaseProvider.get());
    }

    private MaintenanceDao maintenanceDao() {
      return DatabaseModule_ProvideMaintenanceDaoFactory.provideMaintenanceDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDatabaseProvider = new DelegateFactory<>();
      this.provideEmirateDaoProvider = new SwitchingProvider<>(singletonCImpl, 3);
      this.provideBrandDaoProvider = new SwitchingProvider<>(singletonCImpl, 4);
      DelegateFactory.setDelegate(provideDatabaseProvider, DoubleCheck.provider(new SwitchingProvider<CocDatabase>(singletonCImpl, 2)));
      this.vehicleRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<VehicleRepository>(singletonCImpl, 1));
      this.reminderRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<ReminderRepository>(singletonCImpl, 5));
      this.provideDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<DataStore<Preferences>>(singletonCImpl, 7));
      this.userPreferencesProvider = DoubleCheck.provider(new SwitchingProvider<UserPreferences>(singletonCImpl, 6));
      this.notificationHelperProvider = DoubleCheck.provider(new SwitchingProvider<NotificationHelper>(singletonCImpl, 8));
      this.reminderWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<ReminderWorker_AssistedFactory>(singletonCImpl, 0));
      this.brandRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<BrandRepository>(singletonCImpl, 9));
      this.emirateRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<EmirateRepository>(singletonCImpl, 10));
      this.mileageRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<MileageRepository>(singletonCImpl, 11));
      this.insuranceRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<InsuranceRepository>(singletonCImpl, 12));
      this.maintenanceRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<MaintenanceRepository>(singletonCImpl, 13));
    }

    @Override
    public void injectMyCarUaeApp(MyCarUaeApp myCarUaeApp) {
      injectMyCarUaeApp2(myCarUaeApp);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private MyCarUaeApp injectMyCarUaeApp2(MyCarUaeApp instance) {
      MyCarUaeApp_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.mycaruae.app.notification.ReminderWorker_AssistedFactory 
          return (T) new ReminderWorker_AssistedFactory() {
            @Override
            public ReminderWorker create(Context appContext, WorkerParameters workerParams) {
              return new ReminderWorker(appContext, workerParams, singletonCImpl.vehicleRepositoryProvider.get(), singletonCImpl.reminderRepositoryProvider.get(), singletonCImpl.userPreferencesProvider.get(), singletonCImpl.notificationHelperProvider.get());
            }
          };

          case 1: // com.mycaruae.app.data.repository.VehicleRepository 
          return (T) new VehicleRepository(singletonCImpl.vehicleDao());

          case 2: // com.mycaruae.app.data.database.CocDatabase 
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideEmirateDaoProvider, singletonCImpl.provideBrandDaoProvider);

          case 3: // com.mycaruae.app.data.database.dao.EmirateDao 
          return (T) DatabaseModule_ProvideEmirateDaoFactory.provideEmirateDao(singletonCImpl.provideDatabaseProvider.get());

          case 4: // com.mycaruae.app.data.database.dao.BrandDao 
          return (T) DatabaseModule_ProvideBrandDaoFactory.provideBrandDao(singletonCImpl.provideDatabaseProvider.get());

          case 5: // com.mycaruae.app.data.repository.ReminderRepository 
          return (T) new ReminderRepository(singletonCImpl.reminderDao());

          case 6: // com.mycaruae.app.data.datastore.UserPreferences 
          return (T) new UserPreferences(singletonCImpl.provideDataStoreProvider.get());

          case 7: // androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> 
          return (T) DataStoreModule_ProvideDataStoreFactory.provideDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 8: // com.mycaruae.app.notification.NotificationHelper 
          return (T) new NotificationHelper(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 9: // com.mycaruae.app.data.repository.BrandRepository 
          return (T) new BrandRepository(singletonCImpl.provideBrandDaoProvider.get());

          case 10: // com.mycaruae.app.data.repository.EmirateRepository 
          return (T) new EmirateRepository(singletonCImpl.provideEmirateDaoProvider.get());

          case 11: // com.mycaruae.app.data.repository.MileageRepository 
          return (T) new MileageRepository(singletonCImpl.mileageLogDao(), singletonCImpl.vehicleDao());

          case 12: // com.mycaruae.app.data.repository.InsuranceRepository 
          return (T) new InsuranceRepository(singletonCImpl.insuranceDao());

          case 13: // com.mycaruae.app.data.repository.MaintenanceRepository 
          return (T) new MaintenanceRepository(singletonCImpl.maintenanceDao());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
