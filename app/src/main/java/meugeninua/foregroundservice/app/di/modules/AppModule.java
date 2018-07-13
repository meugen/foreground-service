package meugeninua.foregroundservice.app.di.modules;

import android.content.Context;

import dagger.Binds;
import dagger.Module;
import meugeninua.foregroundservice.app.ForegroundApp;
import meugeninua.foregroundservice.app.di.qualifiers.AppContext;
import meugeninua.foregroundservice.app.di.scopes.PerApplication;

@Module
public interface AppModule {

    @Binds @AppContext @PerApplication
    Context bindAppContext(ForegroundApp app);
}
