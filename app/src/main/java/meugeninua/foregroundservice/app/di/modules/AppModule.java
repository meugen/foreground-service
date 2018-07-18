package meugeninua.foregroundservice.app.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import meugeninua.foregroundservice.app.ForegroundApp;
import meugeninua.foregroundservice.app.content.SharedPrefs;
import meugeninua.foregroundservice.app.content.SharedPrefsImpl;
import meugeninua.foregroundservice.app.di.qualifiers.AppContext;

@Module
public interface AppModule {

    @Binds @AppContext
    Context bindAppContext(ForegroundApp app);

    @Binds @Singleton
    SharedPrefs bindSharedPrefs(SharedPrefsImpl impl);
}
