package meugeninua.foregroundservice.app.managers;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import meugeninua.foregroundservice.R;
import meugeninua.foregroundservice.app.di.qualifiers.AppContext;
import meugeninua.foregroundservice.app.services.foreground.ForegroundService;
import meugeninua.foregroundservice.app.services.jobs.FetchJobService;
import meugeninua.foregroundservice.model.enums.ServiceStatus;

/**
 * @author meugen
 */
@Singleton
public class AppServiceManager {

    private static final String CHANNEL_ID = "foreground";

    @Inject @AppContext Context context;
    @Inject AppPrefsManager prefsManager;
    @Inject FirebaseJobDispatcher dispatcher;

    private final Handler handler;

    @Inject
    AppServiceManager() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    public void startBackgroundDelayed() {
        handler.postDelayed(this::startBackground,
                TimeUnit.SECONDS.toMillis(1));
    }

    public void startBackground() {
        prefsManager.setServiceStatus(ServiceStatus.SERVICE_BACKGROUND);
        new FetchJobService.Launcher()
                .launch(dispatcher);
    }

    public NotificationCompat.Builder newBuilderForNotification() {
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(context.getText(R.string.app_name))
                .setSmallIcon(R.drawable.baseline_import_export_horizontal_black_18);
    }

    public void checkForegroundService(@ServiceStatus final int status) {
        if (status == ServiceStatus.SERVICE_FOREGROUND) {
            ForegroundService.start(context);
        }
    }

    @ServiceStatus
    public int createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _createNotificationChannel();
        }
        @ServiceStatus
        int status = prefsManager.getServiceStatus();
        if (status == ServiceStatus.SERVICE_VOID) {
            prefsManager.setServiceStatus(ServiceStatus.SERVICE_INITIALIZED);
            status = ServiceStatus.SERVICE_INITIALIZED;
        }
        return status;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void _createNotificationChannel() {
        final NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                context.getText(R.string.default_notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(context.getString(R.string.default_notification_channel_description));
        channel.setShowBadge(false);
        channel.setLightColor(context.getColor(R.color.colorPrimary));

        final NotificationManager manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }
}
