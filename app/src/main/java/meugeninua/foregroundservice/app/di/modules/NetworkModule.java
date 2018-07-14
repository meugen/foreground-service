package meugeninua.foregroundservice.app.di.modules;

import dagger.Module;
import dagger.Provides;
import meugeninua.foregroundservice.app.di.scopes.PerApplication;
import okhttp3.OkHttpClient;

@Module
public interface NetworkModule {

    @Provides @PerApplication
    static OkHttpClient provideHttpClient() {
        return new OkHttpClient.Builder()
                .build();
    }
}
