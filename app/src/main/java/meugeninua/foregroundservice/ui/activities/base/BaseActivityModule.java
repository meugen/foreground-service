package meugeninua.foregroundservice.ui.activities.base;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import meugeninua.foregroundservice.app.di.qualifiers.ActivityContext;

@Module
public interface BaseActivityModule {

    @Binds @ActivityContext
    Context bindActivityContext(AppCompatActivity activity);

    @Provides
    static FragmentManager provideFragmentManager(
            final AppCompatActivity activity) {
        return activity.getSupportFragmentManager();
    }
}
