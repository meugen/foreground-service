package meugeninua.foregroundservice.ui.activities.details;

import android.support.v7.app.AppCompatActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import meugeninua.foregroundservice.app.di.scopes.PerActivity;
import meugeninua.foregroundservice.app.di.scopes.PerFragment;
import meugeninua.foregroundservice.ui.activities.base.BaseActivityModule;
import meugeninua.foregroundservice.ui.activities.details.fragments.details.DetailsFragment;
import meugeninua.foregroundservice.ui.activities.details.fragments.details.DetailsFragmentModule;

@Module(includes = BaseActivityModule.class)
public interface DetailsActivityModule {

    @Binds @PerActivity
    AppCompatActivity bindActivity(DetailsActivity activity);

    @ContributesAndroidInjector(modules = DetailsFragmentModule.class)
    @PerFragment
    DetailsFragment contributeDetailsFragment();
}
