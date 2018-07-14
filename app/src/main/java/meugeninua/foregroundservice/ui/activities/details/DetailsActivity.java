package meugeninua.foregroundservice.ui.activities.details;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import meugeninua.foregroundservice.R;
import meugeninua.foregroundservice.model.provider.ProviderConstants;
import meugeninua.foregroundservice.ui.activities.base.BaseActivity;
import meugeninua.foregroundservice.ui.activities.details.fragments.details.DetailsFragment;
import meugeninua.foregroundservice.ui.activities.details.fragments.details.adapters.DetailsAdapter;

public class DetailsActivity extends BaseActivity implements ProviderConstants {

    private static final String EXTRA_RESULT = "result";
    private static final String FRAGMENT_TAG = "details";

    public static void start(final Context context, final int result) {
        final Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(EXTRA_RESULT, result);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag(FRAGMENT_TAG) == null) {
            final int result = getIntent().getIntExtra(EXTRA_RESULT, 0);
            fm.beginTransaction()
                    .add(R.id.container, DetailsFragment.build(result), FRAGMENT_TAG)
                    .commit();
        }
    }
}
