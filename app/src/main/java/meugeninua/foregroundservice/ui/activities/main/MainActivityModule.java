package meugeninua.foregroundservice.ui.activities.main;

import android.support.v7.app.AppCompatActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import meugeninua.foregroundservice.ui.activities.base.BaseActivityModule;
import meugeninua.foregroundservice.ui.activities.main.fragments.choose.ChooseServiceModeDialog;
import meugeninua.foregroundservice.ui.activities.main.fragments.choose.ChooseServiceModeDialogModule;
import meugeninua.foregroundservice.ui.activities.main.fragments.main.MainFragment;
import meugeninua.foregroundservice.ui.activities.main.fragments.main.MainFragmentModule;

@Module(includes = BaseActivityModule.class)
public interface MainActivityModule {

    @Binds
    AppCompatActivity bindActivity(MainActivity activity);

    @ContributesAndroidInjector(modules = MainFragmentModule.class)
    MainFragment contributeMainFragment();

    @ContributesAndroidInjector(modules = ChooseServiceModeDialogModule.class)
    ChooseServiceModeDialog contributeChooseServiceModeDialog();
}
