package meugeninua.foregroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        MainAdapter.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private Button startView;
    private Button stopView;

    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startView = findViewById(R.id.start);
        stopView = findViewById(R.id.stop);
        recyclerView = findViewById(R.id.recycler);
        startView.setOnClickListener(this);
        stopView.setOnClickListener(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL));

        getSupportLoaderManager().initLoader(0, null, new MainCallbacks());
    }

    @Override
    public void onClick(final View v) {
        final int viewId = v.getId();
        if (viewId == R.id.start) {
            onStartService();
        } else if (viewId == R.id.stop) {
            onStopService();
        }
    }

    private void onStartService() {
        final Intent intent = new Intent(this, ForegroundService.class);
        startService(intent);
    }

    private void onStopService() {
        final Intent intent = new Intent(this, ForegroundService.class);
        stopService(intent);
    }

    private void setupCursor(final Cursor cursor) {
        if (adapter == null) {
            adapter = new MainAdapter(this, this, cursor);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.swapCursor(cursor);
        }
    }

    @Override
    public void onItemSelected(final int result) {
        DetailsActivity.start(this, result);
    }

    private class MainCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(final int id, @Nullable final Bundle args) {
            final Uri uri = new Uri.Builder()
                    .scheme("content")
                    .authority(ForegroundProvider.AUTHORITY)
                    .appendPath("stats")
                    .build();
            return new CursorLoader(MainActivity.this, uri, null, null, null, null);
        }

        @Override
        public void onLoadFinished(@NonNull final Loader<Cursor> loader, final Cursor data) {
            setupCursor(data);
        }

        @Override
        public void onLoaderReset(@NonNull final Loader<Cursor> loader) {}
    }
}
