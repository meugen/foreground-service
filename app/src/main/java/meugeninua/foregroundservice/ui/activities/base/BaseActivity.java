package meugeninua.foregroundservice.ui.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.binding.Binding;

public abstract class BaseActivity<B extends Binding> extends AppCompatActivity
        implements HasSupportFragmentInjector {

    @Inject protected B binding;
    @Inject DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        binding.attachView(getWindow().getDecorView());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.detachView();
    }
}
