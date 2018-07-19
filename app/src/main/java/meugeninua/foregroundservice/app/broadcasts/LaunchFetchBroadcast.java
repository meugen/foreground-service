package meugeninua.foregroundservice.app.broadcasts;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import meugeninua.foregroundservice.app.services.jobs.FetchJobService;

/**
 * @author meugen
 */
public class LaunchFetchBroadcast extends BroadcastReceiver {

    public static PendingIntent build(final Context context) {
        final Intent intent = new Intent(context,
                LaunchFetchBroadcast.class);
        return PendingIntent.getBroadcast(context,
                0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    @Inject FirebaseJobDispatcher dispatcher;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        AndroidInjection.inject(this, context);
        new FetchJobService.Launcher()
                .launch(dispatcher);
    }
}
