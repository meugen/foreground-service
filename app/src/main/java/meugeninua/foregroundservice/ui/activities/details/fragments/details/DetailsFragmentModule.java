package meugeninua.foregroundservice.ui.activities.details.fragments.details;

import dagger.Binds;
import dagger.Module;
import meugeninua.foregroundservice.app.di.scopes.PerFragment;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.BaseFragmentModule;
import meugeninua.foregroundservice.ui.activities.details.fragments.details.binding.DetailsBinding;
import meugeninua.foregroundservice.ui.activities.details.fragments.details.binding.DetailsBindingImpl;

@Module(includes = BaseFragmentModule.class)
public interface DetailsFragmentModule {

    @Binds @PerFragment
    DetailsBinding bindBinding(DetailsBindingImpl impl);
}
