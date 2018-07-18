package meugeninua.foregroundservice.app;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.os.AsyncTask;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasBroadcastReceiverInjector;
import dagger.android.HasContentProviderInjector;
import dagger.android.HasServiceInjector;
import meugeninua.foregroundservice.BuildConfig;
import meugeninua.foregroundservice.app.di.DaggerAppComponent;
import meugeninua.foregroundservice.app.managers.AppServiceManager;
import meugeninua.foregroundservice.model.enums.ServiceStatus;
import timber.log.Timber;

public class ForegroundApp extends Application implements HasActivityInjector,
        HasServiceInjector, HasContentProviderInjector, HasBroadcastReceiverInjector {

    @Inject DispatchingAndroidInjector<Activity> activityInjector;
    @Inject DispatchingAndroidInjector<Service> serviceInjector;
    @Inject DispatchingAndroidInjector<ContentProvider> providerInjector;
    @Inject DispatchingAndroidInjector<BroadcastReceiver> receiverInjector;

    @Inject AppServiceManager serviceManager;

    private boolean needToInject = true;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        injectIfNeeded();

        new CheckServicesTask(serviceManager).execute();
    }

    private void injectIfNeeded() {
        if (needToInject) {
            synchronized (this) {
                if (needToInject) {
                    DaggerAppComponent.builder()
                            .create(this)
                            .inject(this);
                    needToInject = false;
                }
            }
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return serviceInjector;
    }

    @Override
    public AndroidInjector<ContentProvider> contentProviderInjector() {
        injectIfNeeded();
        return providerInjector;
    }

    @Override
    public AndroidInjector<BroadcastReceiver> broadcastReceiverInjector() {
        return receiverInjector;
    }

    private static class CheckServicesTask extends AsyncTask<Void, Void, Void> {

        private final AppServiceManager serviceManager;

        CheckServicesTask(
                final AppServiceManager serviceManager) {
            this.serviceManager = serviceManager;
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            @ServiceStatus
            final int status = serviceManager.createNotificationChannel();
            serviceManager.checkForegroundService(status);
            return null;
        }
    }
}
