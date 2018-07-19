package meugeninua.foregroundservice.app.services.jobs;

import android.os.Bundle;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import meugeninua.foregroundservice.BuildConfig;
import meugeninua.foregroundservice.app.managers.AppAlarmsManager;
import meugeninua.foregroundservice.app.managers.AppPrefsManager;
import meugeninua.foregroundservice.model.actions.AppActionApi;
import meugeninua.foregroundservice.model.enums.ServiceStatus;

/**
 * @author meugen
 */
public class FetchJobService extends JobService {

    @Inject ScheduledExecutorService executor;
    @Inject AppActionApi actionApi;
    @Inject AppAlarmsManager alarmsManager;
    @Inject AppPrefsManager prefsManager;

    private AtomicReference<Future<?>> futureRef;

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();

        futureRef = new AtomicReference<>();
    }

    @Override
    public boolean onStartJob(final JobParameters job) {
        final Future<?> future = executor
                .submit(() -> doAction(job));
        futureRef.set(future);
        return true;
    }

    private void doAction(final JobParameters job) {
        try {
            if (prefsManager.getServiceStatus() != ServiceStatus.SERVICE_BACKGROUND) {
                return;
            }
            actionApi.onAction();
            alarmsManager.launchNextFetch(BuildConfig.FETCH_PERIOD_MILLIS);
        } finally {
            jobFinished(job, false);
            futureRef.set(null);
        }
    }

    @Override
    public boolean onStopJob(final JobParameters job) {
        final Future<?> future = futureRef.getAndSet(null);
        if (future != null && !future.isCancelled() && !future.isDone()) {
            future.cancel(true);
            return true;
        }
        return false;
    }

    public static class Launcher {

        private final Bundle extras = new Bundle();

        public void launch(final FirebaseJobDispatcher dispatcher) {
            final Job job = dispatcher.newJobBuilder()
                    .setService(FetchJobService.class)
                    .setRecurring(false)
                    .setTrigger(Trigger.NOW)
                    .setConstraints(
                            Constraint.ON_ANY_NETWORK)
                    .setTag("fetch")
                    .setExtras(extras)
                    .setReplaceCurrent(true)
                    .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                    .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                    .build();
            dispatcher.mustSchedule(job);
        }
    }
}
