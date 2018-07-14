package meugeninua.foregroundservice.app.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import meugeninua.foregroundservice.app.di.scopes.PerActivity;
import meugeninua.foregroundservice.app.di.scopes.PerProvider;
import meugeninua.foregroundservice.app.di.scopes.PerService;
import meugeninua.foregroundservice.app.services.foreground.ForegroundService;
import meugeninua.foregroundservice.app.services.foreground.ForegroundServiceModule;
import meugeninua.foregroundservice.model.providers.foreground.ForegroundProvider;
import meugeninua.foregroundservice.model.providers.foreground.ForegroundProviderModule;
import meugeninua.foregroundservice.model.providers.prefs.PrefsProvider;
import meugeninua.foregroundservice.model.providers.prefs.PrefsProviderModule;
import meugeninua.foregroundservice.ui.activities.details.DetailsActivity;
import meugeninua.foregroundservice.ui.activities.details.DetailsActivityModule;
import meugeninua.foregroundservice.ui.activities.main.MainActivity;
import meugeninua.foregroundservice.ui.activities.main.MainActivityModule;

@Module
public interface ComponentsModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    @PerActivity
    MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = DetailsActivityModule.class)
    @PerActivity
    DetailsActivity contributeDetailsActivity();

    @ContributesAndroidInjector(modules = ForegroundServiceModule.class)
    @PerService
    ForegroundService contributeForegroundService();

    @ContributesAndroidInjector(modules = ForegroundProviderModule.class)
    @PerProvider
    ForegroundProvider contributeForegroundProvider();

    @ContributesAndroidInjector(modules = PrefsProviderModule.class)
    @PerProvider
    PrefsProvider contributePrefsProvider();
}
