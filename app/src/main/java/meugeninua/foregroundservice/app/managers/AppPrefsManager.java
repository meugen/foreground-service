package meugeninua.foregroundservice.app.managers;

import android.content.Context;

import java.util.concurrent.ScheduledExecutorService;

import javax.inject.Inject;
import javax.inject.Singleton;

import meugeninua.foregroundservice.app.broadcasts.OnEventBroadcast;
import meugeninua.foregroundservice.app.content.prefs.SharedPrefs;
import meugeninua.foregroundservice.app.di.qualifiers.AppContext;
import meugeninua.foregroundservice.app.managers.events.ServiceStatusChangedEvent;
import meugeninua.foregroundservice.model.enums.ServiceStatus;

/**
 * @author meugen
 */
@Singleton
public class AppPrefsManager {

    private static final String PREF_SERVICE_STATUS = "service_status";

    @Inject @AppContext Context context;
    @Inject SharedPrefs sharedPrefs;
    @Inject ScheduledExecutorService executor;

    @Inject
    AppPrefsManager() {}

    public void setServiceStatus(@ServiceStatus final int status) {
        sharedPrefs.putInt(PREF_SERVICE_STATUS, status);
        OnEventBroadcast.send(context, new ServiceStatusChangedEvent(status));
    }

    public int getServiceStatus() {
        return sharedPrefs.getInt(PREF_SERVICE_STATUS,
                ServiceStatus.SERVICE_VOID);
    }

    public void getServiceStatusAsync() {
        executor.execute(this::_getServiceStatusAsync);
    }

    private void _getServiceStatusAsync() {
        final int status = getServiceStatus();
        OnEventBroadcast.send(context, new ServiceStatusChangedEvent(status));
    }
}
