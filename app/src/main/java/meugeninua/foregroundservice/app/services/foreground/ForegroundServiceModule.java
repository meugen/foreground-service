package meugeninua.foregroundservice.app.services.foreground;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import dagger.Module;
import dagger.Provides;
import meugeninua.foregroundservice.app.di.scopes.PerService;

@Module
public interface ForegroundServiceModule {

    @Provides @PerService
    static ScheduledExecutorService provideExecutor() {
        return Executors.newScheduledThreadPool(2);
    }
}
