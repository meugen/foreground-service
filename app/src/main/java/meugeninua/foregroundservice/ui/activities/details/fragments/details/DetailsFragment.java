package meugeninua.foregroundservice.ui.activities.details.fragments.details;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import meugeninua.foregroundservice.R;
import meugeninua.foregroundservice.app.di.qualifiers.ActivityContext;
import meugeninua.foregroundservice.model.providers.foreground.ForegroundProviderConstants;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.BaseFragment;
import meugeninua.foregroundservice.ui.activities.details.fragments.details.binding.DetailsBinding;

public class DetailsFragment extends BaseFragment<DetailsBinding> implements ForegroundProviderConstants {

    private static final String PARAM_RESULT = "result";

    public static DetailsFragment build(final int result) {
        final Bundle args = new Bundle();
        args.putInt(PARAM_RESULT, result);

        final DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject @ActivityContext Context context;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details,
                container, false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setupRecycler();

        getLoaderManager().initLoader(0, getArguments(), new DetailsCallbacks());
    }

    private class DetailsCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(final int id, @Nullable final Bundle args) {
            final int result = args == null ? 0 : args.getInt(PARAM_RESULT, 0);
            return new CursorLoader(context,
                    REQUESTS_URI, null, "result=?",
                    new String[] { Integer.toString(result) }, null);
        }

        @Override
        public void onLoadFinished(@NonNull final Loader<Cursor> loader, final Cursor data) {
            binding.setupCursor(data);
        }

        @Override
        public void onLoaderReset(@NonNull final Loader<Cursor> loader) {

        }
    }
}
