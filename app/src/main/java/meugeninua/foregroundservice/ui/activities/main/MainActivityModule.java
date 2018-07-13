package meugeninua.foregroundservice.ui.activities.main;

import android.support.v7.app.AppCompatActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import meugeninua.foregroundservice.app.di.scopes.PerActivity;
import meugeninua.foregroundservice.app.di.scopes.PerFragment;
import meugeninua.foregroundservice.ui.activities.base.BaseActivityModule;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.binding.Binding;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.binding.BindingImpl;
import meugeninua.foregroundservice.ui.activities.main.fragments.main.MainFragment;
import meugeninua.foregroundservice.ui.activities.main.fragments.main.MainFragmentModule;

@Module(includes = BaseActivityModule.class)
public interface MainActivityModule {

    @Binds @PerActivity
    AppCompatActivity bindActivity(MainActivity activity);

    @Binds @PerActivity
    Binding bindBinding(BindingImpl impl);

    @ContributesAndroidInjector(modules = MainFragmentModule.class)
    @PerFragment
    MainFragment contributeMainFragment();
}
