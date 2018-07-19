package meugeninua.foregroundservice.app.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.SystemClock;
import android.support.v4.app.AlarmManagerCompat;

import javax.inject.Inject;
import javax.inject.Singleton;

import meugeninua.foregroundservice.app.broadcasts.LaunchFetchBroadcast;
import meugeninua.foregroundservice.app.di.qualifiers.AppContext;

/**
 * @author meugen
 */
@Singleton
public class AppAlarmsManager {

    @Inject @AppContext Context context;
    @Inject AlarmManager manager;

    @Inject
    AppAlarmsManager() {}

    public void launchNextFetch(final long millis) {
        final PendingIntent intent = LaunchFetchBroadcast
                .build(context);
        final long triggerAtMillis = SystemClock.elapsedRealtime() + millis;
        AlarmManagerCompat.setExact(manager,
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerAtMillis, intent);
    }
}
