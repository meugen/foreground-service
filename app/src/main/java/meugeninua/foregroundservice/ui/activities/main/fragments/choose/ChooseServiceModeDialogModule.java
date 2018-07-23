package meugeninua.foregroundservice.ui.activities.main.fragments.choose;

import dagger.Binds;
import dagger.Module;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.BaseFragmentModule;
import meugeninua.foregroundservice.ui.activities.main.fragments.choose.binding.ChooseServiceModeBinding;
import meugeninua.foregroundservice.ui.activities.main.fragments.choose.binding.ChooseServiceModeBindingImpl;

/**
 * @author meugen
 */
@Module(includes = BaseFragmentModule.class)
public interface ChooseServiceModeDialogModule {

    @Binds
    ChooseServiceModeBinding bindBinding(ChooseServiceModeBindingImpl impl);
}
