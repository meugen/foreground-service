package meugeninua.foregroundservice.ui.activities.details.fragments.details.binding;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import meugeninua.foregroundservice.R;
import meugeninua.foregroundservice.app.di.qualifiers.ActivityContext;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.binding.BaseBinding;
import meugeninua.foregroundservice.ui.activities.details.fragments.details.adapters.DetailsAdapter;

public class DetailsBindingImpl extends BaseBinding implements DetailsBinding {

    @Inject @ActivityContext Context context;
    @Inject DetailsAdapter adapter;

    @Inject
    DetailsBindingImpl() {}

    @Override
    public void setupRecycler() {
        final RecyclerView recyclerView = get(R.id.recycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setupCursor(final Cursor cursor) {
        adapter.swapCursor(cursor);
    }
}
