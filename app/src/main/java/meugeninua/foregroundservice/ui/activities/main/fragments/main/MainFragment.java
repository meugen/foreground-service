package meugeninua.foregroundservice.ui.activities.main.fragments.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import meugeninua.foregroundservice.R;
import meugeninua.foregroundservice.app.di.qualifiers.ActivityContext;
import meugeninua.foregroundservice.app.managers.AppEventsManager;
import meugeninua.foregroundservice.app.managers.AppServiceManager;
import meugeninua.foregroundservice.app.managers.events.ServiceModeChosenEvent;
import meugeninua.foregroundservice.app.managers.events.ServiceStatusChangedEvent;
import meugeninua.foregroundservice.app.services.foreground.ForegroundService;
import meugeninua.foregroundservice.model.providers.foreground.ForegroundProviderConstants;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.BaseFragment;
import meugeninua.foregroundservice.ui.activities.details.DetailsActivity;
import meugeninua.foregroundservice.ui.activities.main.fragments.choose.ChooseServiceModeDialog;
import meugeninua.foregroundservice.ui.activities.main.fragments.main.binding.MainBinding;
import meugeninua.foregroundservice.ui.activities.main.fragments.main.view.ArchMainView;

public class MainFragment extends BaseFragment<MainBinding>
        implements ArchMainView, ForegroundProviderConstants {

    private static final String PREF_CHOSEN_SERVICE_MODE = "chosen_service_mode";

    @Inject @ActivityContext Context context;
    @Inject AppEventsManager eventsManager;
    @Inject AppServiceManager serviceManager;
    @Inject SharedPreferences preferences;
    @Inject FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,
                container, false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setupRecycler();
        binding.setupListeners(this);
        binding.enableButtons();

        getLoaderManager().initLoader(0, null, new MainCallbacks());
        eventsManager.subscribeToEvent(this, ServiceStatusChangedEvent.class,
                event -> binding.enableButtons(event.newServiceStatus));
        eventsManager.subscribeToEvent(this, ServiceModeChosenEvent.class,
                this::onEventChosen);
    }

    private void onEventChosen(final ServiceModeChosenEvent event) {
        preferences.edit()
                .putInt(PREF_CHOSEN_SERVICE_MODE, event.mode)
                .apply();
        startService(event.mode);
    }

    @Override
    public void onStartClick() {
        final int serviceMode = preferences.getInt(
                PREF_CHOSEN_SERVICE_MODE, -1);
        if (!startService(serviceMode)) {
            new ChooseServiceModeDialog().show(fragmentManager,
                    "choose-service-mode");
        }
    }

    private boolean startService(final int serviceMode) {
        if (serviceMode == ServiceModeChosenEvent.BACKGROUND) {
            serviceManager.startBackground();
            return true;
        } else if (serviceMode == ServiceModeChosenEvent.FOREGROUND) {
            ForegroundService.start(context);
            return true;
        }
        return false;
    }

    @Override
    public void onStopClick() {
        serviceManager.stopService();
    }

    @Override
    public void onMoveForeground() {
        ForegroundService.start(context);
    }

    @Override
    public void onMoveBackground() {
        ForegroundService.moveBackground(context);
    }

    @Override
    public void onItemSelected(final int result) {
        DetailsActivity.start(context, result);
    }

    private class MainCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(final int id, @Nullable final Bundle args) {
            return new CursorLoader(context,
                    STATS_URI, null, null,
                    null, null);
        }

        @Override
        public void onLoadFinished(@NonNull final Loader<Cursor> loader, final Cursor data) {
            binding.setupCursor(data);
        }

        @Override
        public void onLoaderReset(@NonNull final Loader<Cursor> loader) {}
    }
}
