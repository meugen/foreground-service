package meugeninua.foregroundservice.app.di.modules;

import android.content.Context;

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

    @Provides @Singleton
    static ScheduledExecutorService provideExecutor() {
        return Executors.newScheduledThreadPool(2);
    }
}
