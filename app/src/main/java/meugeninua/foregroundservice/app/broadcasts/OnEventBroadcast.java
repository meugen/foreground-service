package meugeninua.foregroundservice.app.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import meugeninua.foregroundservice.app.managers.AppEventsManager;

/**
 * @author meugen
 */
public class OnEventBroadcast extends BroadcastReceiver {

    private static final String EXTRA_EVENT = "event";

    public static void send(
            final Context context,
            final Parcelable event) {
        final Intent intent = new Intent(
                context, OnEventBroadcast.class);
        intent.putExtra(EXTRA_EVENT, event);
        context.sendBroadcast(intent);
    }

    @Inject AppEventsManager eventsManager;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        AndroidInjection.inject(this, context);

        final Parcelable event = intent.getParcelableExtra(EXTRA_EVENT);
        if (event != null) {
            eventsManager.post(event);
        }
    }
}
