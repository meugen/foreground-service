package meugeninua.foregroundservice.ui.activities.main.fragments.main.binding;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import meugeninua.foregroundservice.R;
import meugeninua.foregroundservice.app.di.qualifiers.ActivityContext;
import meugeninua.foregroundservice.model.enums.ServiceStatus;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.binding.BaseBinding;
import meugeninua.foregroundservice.ui.activities.main.fragments.main.adapters.MainAdapter;
import meugeninua.foregroundservice.ui.activities.main.fragments.main.view.ArchMainView;
import timber.log.Timber;

public class MainBindingImpl extends BaseBinding implements MainBinding {

    @Inject @ActivityContext Context context;
    @Inject MainAdapter adapter;

    @Inject
    MainBindingImpl() {}

    @Override
    public void setupRecycler() {
        final RecyclerView recyclerView = get(R.id.recycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setupListeners(final ArchMainView view) {
        final OnClickListenerImpl impl = new OnClickListenerImpl(view);
        get(R.id.start).setOnClickListener(impl);
        get(R.id.stop).setOnClickListener(impl);

        adapter.setListener(view);
    }

    @Override
    public void setupCursor(final Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void enableButtons(@ServiceStatus final int status) {
        Timber.d("enableButtons(%d) method called", status);
        get(R.id.start).setEnabled((status & ServiceStatus.MASK_CAN_BE_STARTED) != 0);
        get(R.id.stop).setEnabled((status & ServiceStatus.MASK_CAN_BE_STOPPED) != 0);
    }

    private static class OnClickListenerImpl implements View.OnClickListener {

        private final WeakReference<ArchMainView> viewRef;

        OnClickListenerImpl(final ArchMainView view) {
            viewRef = new WeakReference<>(view);
        }

        @Override
        public void onClick(final View v) {
            final ArchMainView view = viewRef.get();
            if (view == null) {
                return;
            }
            final int id = v.getId();
            if (id == R.id.start) {
                view.onStartClick();
            } else if (id == R.id.stop) {
                view.onStopClick();
            }
        }
    }
}
