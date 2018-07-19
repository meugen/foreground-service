package meugeninua.foregroundservice.ui.activities.main.fragments.choose;

import dagger.Binds;
import dagger.Module;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.BaseFragmentModule;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.binding.Binding;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.binding.BindingImpl;

/**
 * @author meugen
 */
@Module(includes = BaseFragmentModule.class)
public interface ChooseServiceModeDialogModule {

    @Binds
    Binding bindBinding(BindingImpl impl);
}
