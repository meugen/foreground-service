package meugeninua.foregroundservice.app.services.foreground;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import meugeninua.foregroundservice.BuildConfig;
import meugeninua.foregroundservice.R;
import meugeninua.foregroundservice.app.managers.AppPrefsManager;
import meugeninua.foregroundservice.app.managers.AppServiceManager;
import meugeninua.foregroundservice.model.actions.AppActionApi;
import meugeninua.foregroundservice.model.enums.ServiceStatus;
import meugeninua.foregroundservice.model.providers.foreground.ForegroundProviderConstants;

public class ForegroundService extends Service implements ForegroundProviderConstants {

    private static final int NOTIFICATION_ID = 1000;
    private static final String STOP_ACTION = "meugeninua.foreground.action.STOP";
    private static final String MOVE_BACKGROUND_ACTION = "meugeninua.foreground.action.MOVE_BACKGROUND";

    private static Intent build(final Context context) {
        return new Intent(context, ForegroundService.class);
    }

    public static void start(final Context context) {
        context.startService(build(context));
    }

    public static void stop(final Context context) {
        context.stopService(build(context));
    }

    public static void moveBackground(final Context context) {
        final Intent intent = new Intent(
                MOVE_BACKGROUND_ACTION);
        context.sendBroadcast(intent);
    }

    private StopReceiver receiver;
    private PowerManager.WakeLock wakeLock;
    private ScheduledExecutorService executor;

    @Inject AppActionApi actionApi;
    @Inject AppPrefsManager prefsManager;
    @Inject AppServiceManager serviceManager;

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();

        this.receiver = new StopReceiver(serviceManager);
        final IntentFilter filter = new IntentFilter();
        filter.addAction(STOP_ACTION);
        filter.addAction(MOVE_BACKGROUND_ACTION);
        registerReceiver(receiver, filter);

        final PendingIntent stopIntent = PendingIntent.getBroadcast(
                this, 0, new Intent(STOP_ACTION),
                PendingIntent.FLAG_CANCEL_CURRENT);
        final PendingIntent moveBackgroundIntent = PendingIntent.getBroadcast(
                this, 0, new Intent(MOVE_BACKGROUND_ACTION),
                PendingIntent.FLAG_CANCEL_CURRENT);

        final NotificationCompat.Builder builder = serviceManager.newBuilderForNotification()
                .addAction(0, getText(R.string.button_move_background), moveBackgroundIntent)
                .addAction(R.drawable.baseline_stop_black_18, getText(R.string.button_stop), stopIntent);
        startForeground(NOTIFICATION_ID, builder.build());

        final PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        this.wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "foreground");
        this.wakeLock.acquire();
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        if (executor != null) {
            // The service is already started
            return START_STICKY;
        }

        this.executor = Executors.newScheduledThreadPool(2);
        executor.execute(() -> prefsManager
                .setServiceStatus(ServiceStatus.SERVICE_FOREGROUND));
        executor.scheduleWithFixedDelay(new RunnableImpl(this),
                0, BuildConfig.FETCH_PERIOD_MILLIS, TimeUnit.MILLISECONDS);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);

        executor.execute(() -> prefsManager
                .setServiceStatus(ServiceStatus.SERVICE_STOPPED));
        executor.shutdown();
        executor = null;

        wakeLock.release();
        wakeLock = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static class StopReceiver extends BroadcastReceiver {

        private final AppServiceManager serviceManager;

        public StopReceiver(final AppServiceManager serviceManager) {
            this.serviceManager = serviceManager;
        }

        @Override
        public void onReceive(final Context context, final Intent intent) {
            final String action = intent.getAction();
            if (STOP_ACTION.equals(action)) {
                stopForegroundService(context);
            } else if (MOVE_BACKGROUND_ACTION.equals(action)) {
                stopForegroundService(context);
                serviceManager.startBackgroundDelayed();
            }
        }

        private void stopForegroundService(final Context context) {
            final Intent serviceIntent = new Intent(context,
                    ForegroundService.class);
            context.stopService(serviceIntent);
        }
    }

    private static class RunnableImpl implements Runnable {

        private final WeakReference<ForegroundService> ref;

        RunnableImpl(final ForegroundService service) {
            this.ref = new WeakReference<>(service);
        }

        @Override
        public void run() {
            final ForegroundService service = ref.get();
            if (service != null) {
                service.actionApi.onAction();
            }
        }
    }
}
