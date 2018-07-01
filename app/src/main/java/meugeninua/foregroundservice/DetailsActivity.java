package meugeninua.foregroundservice;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

public class DetailsActivity extends AppCompatActivity {

    private static final String EXTRA_RESULT = "result";

    public static void start(final Context context, final int result) {
        final Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(EXTRA_RESULT, result);
        context.startActivity(intent);
    }

    private RecyclerView recyclerView;
    private DetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL));

        getSupportLoaderManager().initLoader(0,
                getIntent().getExtras(),
                new DetailsCallbacks());
    }

    private void setupData(final Cursor cursor) {
        if (adapter == null) {
            adapter = new DetailsAdapter(this, cursor);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.swapCursor(cursor);
        }
    }

    private class DetailsCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(final int id, @Nullable final Bundle args) {
            final int result = args == null ? 0 : args.getInt(EXTRA_RESULT, 0);
            final Uri uri = new Uri.Builder()
                    .scheme("content")
                    .authority(ForegroundProvider.AUTHORITY)
                    .appendPath("requests")
                    .build();
            return new CursorLoader(DetailsActivity.this,
                    uri, null, "result=?",
                    new String[] { Integer.toString(result) }, null);
        }

        @Override
        public void onLoadFinished(@NonNull final Loader<Cursor> loader, final Cursor data) {
            setupData(data);
        }

        @Override
        public void onLoaderReset(@NonNull final Loader<Cursor> loader) {

        }
    }
}
