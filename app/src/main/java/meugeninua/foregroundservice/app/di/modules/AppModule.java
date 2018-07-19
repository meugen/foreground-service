package meugeninua.foregroundservice.app.di.modules;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import meugeninua.foregroundservice.app.ForegroundApp;
import meugeninua.foregroundservice.app.content.prefs.SharedPrefs;
import meugeninua.foregroundservice.app.content.prefs.SharedPrefsImpl;
import meugeninua.foregroundservice.app.di.qualifiers.AppContext;

@Module
public interface AppModule {

    @Binds @AppContext
    Context bindAppContext(ForegroundApp app);

    @Binds
    SharedPrefs bindSharedPrefs(SharedPrefsImpl impl);

    @Provides
    static AlarmManager provideAlarmManager(@AppContext final Context context) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Provides @Singleton
    static ScheduledExecutorService provideExecutor() {
        return Executors.newScheduledThreadPool(2);
    }

    @Provides @Singleton
    static FirebaseJobDispatcher provideDispatcher(
            @AppContext final Context context) {
        return new FirebaseJobDispatcher(new GooglePlayDriver(context));
    }

    @Provides @Singleton
    static SharedPreferences provideSharedPreferences(
            @AppContext final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
