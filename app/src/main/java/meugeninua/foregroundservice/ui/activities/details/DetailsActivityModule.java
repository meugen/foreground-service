package meugeninua.foregroundservice.ui.activities.details;

import android.support.v7.app.AppCompatActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import meugeninua.foregroundservice.ui.activities.base.BaseActivityModule;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.binding.Binding;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.binding.BindingImpl;
import meugeninua.foregroundservice.ui.activities.details.fragments.details.DetailsFragment;
import meugeninua.foregroundservice.ui.activities.details.fragments.details.DetailsFragmentModule;

@Module(includes = BaseActivityModule.class)
public interface DetailsActivityModule {

    @Binds
    AppCompatActivity bindActivity(DetailsActivity activity);

    @Binds
    Binding bindBinding(BindingImpl impl);

    @ContributesAndroidInjector(modules = DetailsFragmentModule.class)
    DetailsFragment contributeDetailsFragment();
}
