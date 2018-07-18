package meugeninua.foregroundservice.app.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public interface NetworkModule {

    @Provides @Singleton
    static OkHttpClient provideHttpClient() {
        return new OkHttpClient.Builder()
                .build();
    }
}
