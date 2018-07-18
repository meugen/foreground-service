package meugeninua.foregroundservice.ui.activities.main.fragments.main;

import dagger.Binds;
import dagger.Module;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.BaseFragmentModule;
import meugeninua.foregroundservice.ui.activities.main.fragments.main.binding.MainBinding;
import meugeninua.foregroundservice.ui.activities.main.fragments.main.binding.MainBindingImpl;

@Module(includes = BaseFragmentModule.class)
public interface MainFragmentModule {

    @Binds
    MainBinding bindBinding(MainBindingImpl impl);
}
