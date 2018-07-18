package meugeninua.foregroundservice.app.services.foreground;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import dagger.Module;
import dagger.Provides;

@Module
public interface ForegroundServiceModule {

    @Provides
    static ScheduledExecutorService provideExecutor() {
        return Executors.newScheduledThreadPool(2);
    }
}
