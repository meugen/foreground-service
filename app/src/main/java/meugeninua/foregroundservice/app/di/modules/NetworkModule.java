package meugeninua.foregroundservice.app.di.modules;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import meugeninua.foregroundservice.model.actions.AppActionApi;
import meugeninua.foregroundservice.model.actions.fetch.FetchActionApi;
import okhttp3.OkHttpClient;

@Module
public interface NetworkModule {

    @Provides
    static OkHttpClient provideHttpClient() {
        return new OkHttpClient.Builder()
                .build();
    }

    @Binds
    AppActionApi bindFetchActionApi(FetchActionApi api);
}
