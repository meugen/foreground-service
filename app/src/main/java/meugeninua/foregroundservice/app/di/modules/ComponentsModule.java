package meugeninua.foregroundservice.app.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import meugeninua.foregroundservice.app.di.scopes.PerActivity;
import meugeninua.foregroundservice.ui.activities.main.MainActivity;
import meugeninua.foregroundservice.ui.activities.main.MainActivityModule;

@Module
public interface ComponentsModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    @PerActivity
    MainActivity contributeMainActivity();

//    @ContributesAndroidInjector(modules = DetailsActivityModule.class)
//    @PerActivity
//    DetailsActivity contributeDetailsActivity();
}
