package meugeninua.foregroundservice.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentProvider;
import android.os.Build;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasContentProviderInjector;
import dagger.android.HasServiceInjector;
import meugeninua.foregroundservice.R;
import meugeninua.foregroundservice.app.di.DaggerAppComponent;

public class ForegroundApp extends Application implements HasActivityInjector,
        HasServiceInjector, HasContentProviderInjector {

    public static final String CHANNEL_ID = "foreground";

    @Inject DispatchingAndroidInjector<Activity> activityInjector;
    @Inject DispatchingAndroidInjector<Service> serviceInjector;
    @Inject DispatchingAndroidInjector<ContentProvider> providerInjector;

    private boolean needToInject = true;

    @Override
    public void onCreate() {
        super.onCreate();
        injectIfNeeded();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
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

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        final NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                getText(R.string.default_notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(getString(R.string.default_notification_channel_description));
        channel.setShowBadge(false);
        channel.setLightColor(getColor(R.color.colorPrimary));

        final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }

}
