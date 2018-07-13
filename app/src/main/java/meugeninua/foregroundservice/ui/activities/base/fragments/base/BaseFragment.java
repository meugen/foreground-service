package meugeninua.foregroundservice.ui.activities.base.fragments.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.binding.Binding;

public abstract class BaseFragment<B extends Binding> extends Fragment {

    @Inject protected B binding;

    @Override
    public void onAttach(final Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
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
