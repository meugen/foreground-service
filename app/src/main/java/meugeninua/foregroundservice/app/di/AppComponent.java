package meugeninua.foregroundservice.app.di;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import meugeninua.foregroundservice.app.ForegroundApp;
import meugeninua.foregroundservice.app.di.modules.AppModule;
import meugeninua.foregroundservice.app.di.modules.ComponentsModule;
import meugeninua.foregroundservice.app.di.scopes.PerApplication;

@Component(modules = {AndroidSupportInjectionModule.class,
        AppModule.class, ComponentsModule.class})
@PerApplication
public interface AppComponent extends AndroidInjector<ForegroundApp> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<ForegroundApp> {}
}
