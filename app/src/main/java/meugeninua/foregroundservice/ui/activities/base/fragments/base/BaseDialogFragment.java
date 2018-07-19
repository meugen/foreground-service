package meugeninua.foregroundservice.ui.activities.base.fragments.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.binding.Binding;

/**
 * @author meugen
 */
public class BaseDialogFragment<B extends Binding> extends DialogFragment {

    @Inject protected B binding;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(
            @NonNull final View view,
            @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.attachView(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.detachView();
    }
}
