package meugeninua.foregroundservice.ui.activities.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import meugeninua.foregroundservice.R;
import meugeninua.foregroundservice.model.providers.foreground.ForegroundProviderConstants;
import meugeninua.foregroundservice.ui.activities.base.BaseActivity;
import meugeninua.foregroundservice.ui.activities.details.fragments.details.DetailsFragment;

public class DetailsActivity extends BaseActivity implements ForegroundProviderConstants {

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
