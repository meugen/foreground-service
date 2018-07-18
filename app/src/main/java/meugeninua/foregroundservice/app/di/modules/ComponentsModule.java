package meugeninua.foregroundservice.app.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import meugeninua.foregroundservice.app.broadcasts.OnEventBroadcast;
import meugeninua.foregroundservice.app.services.foreground.ForegroundService;
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
    MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = DetailsActivityModule.class)
    DetailsActivity contributeDetailsActivity();

    @ContributesAndroidInjector
    ForegroundService contributeForegroundService();

    @ContributesAndroidInjector(modules = ForegroundProviderModule.class)
    ForegroundProvider contributeForegroundProvider();

    @ContributesAndroidInjector(modules = PrefsProviderModule.class)
    PrefsProvider contributePrefsProvider();

    @ContributesAndroidInjector
    OnEventBroadcast contributeOnEventBroadcast();
}
