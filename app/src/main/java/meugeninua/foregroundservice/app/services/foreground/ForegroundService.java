package meugeninua.foregroundservice.app.services.foreground;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.Reader;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import meugeninua.foregroundservice.R;
import meugeninua.foregroundservice.app.managers.AppPrefsManager;
import meugeninua.foregroundservice.app.managers.AppServiceManager;
import meugeninua.foregroundservice.model.enums.ServiceStatus;
import meugeninua.foregroundservice.model.providers.foreground.ForegroundProviderConstants;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ForegroundService extends Service implements ForegroundProviderConstants {

    private static final String TAG = ForegroundService.class.getSimpleName();

    private static final int NOTIFICATION_ID = 1000;
    private static final String STOP_ACTION = "meugeninua.foreground.action.STOP";

    private static Intent build(final Context context) {
        return new Intent(context, ForegroundService.class);
    }

    public static void start(final Context context) {
        context.startService(build(context));
    }

    public static void stop(final Context context) {
        context.stopService(build(context));
    }

    private StopReceiver receiver;
    private PowerManager.WakeLock wakeLock;
    private ScheduledExecutorService executor;

    @Inject OkHttpClient client;
    @Inject AppPrefsManager prefsManager;
    @Inject AppServiceManager serviceManager;

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();

        this.receiver = new StopReceiver();
        registerReceiver(receiver, new IntentFilter(STOP_ACTION));

        final PendingIntent stopIntent = PendingIntent.getBroadcast(
                this, 0, new Intent(STOP_ACTION),
                PendingIntent.FLAG_CANCEL_CURRENT);

        final NotificationCompat.Builder builder = serviceManager.newBuilderForNotification()
                .addAction(R.drawable.baseline_stop_black_18, getText(R.string.button_stop), stopIntent);
        startForeground(NOTIFICATION_ID, builder.build());

        final PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        this.wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "foreground");
        this.wakeLock.acquire();
        this.executor = Executors.newScheduledThreadPool(2);

        executor.execute(() -> prefsManager
                .setServiceStatus(ServiceStatus.SERVICE_FOREGROUND));
        executor.scheduleWithFixedDelay(new RunnableImpl(this),
                0, 10, TimeUnit.SECONDS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        this.wakeLock.release();
        executor.execute(() -> prefsManager
                .setServiceStatus(ServiceStatus.SERVICE_STOPPED));
        executor.shutdown();
    }

    private void onCall() {
        final Request request = new Request.Builder()
                .get().url("https://restapi.meugen.in.ua")
                .build();
        final Call call = client.newCall(request);
        try {
            final Response response = call.execute();

            final Reader reader = response.body().charStream();
            final StringBuilder content = new StringBuilder();

            final char[] buf = new char[256];
            while (true) {
                final int count = reader.read(buf);
                if (count < 0) {
                    break;
                }
                content.append(buf, 0, count);
            }
            Log.d(TAG, content.toString());

            insertResult(response.isSuccessful() ? 0 : 1, response.message());
        } catch (Throwable e) {
            Log.e(TAG, e.getMessage(), e);
            insertResult(1, e.getMessage());
        }
    }

    private void insertResult(final int result, final String message) {
        final ContentValues values = new ContentValues();
        values.put("timestamp", System.currentTimeMillis());
        values.put("result", result);
        values.put("message", message);
        getContentResolver().insert(REQUESTS_URI, values);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static class StopReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            final String action = intent.getAction();
            if (STOP_ACTION.equals(action)) {
                final Intent serviceIntent = new Intent(context, ForegroundService.class);
                context.stopService(serviceIntent);
            }
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
                service.onCall();
            }
        }
    }
}
