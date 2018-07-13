package meugeninua.foregroundservice.ui.activities.main.fragments.main.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import meugeninua.foregroundservice.R;
import meugeninua.foregroundservice.app.di.qualifiers.AppContext;
import meugeninua.foregroundservice.app.di.scopes.PerFragment;

@PerFragment
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {

    private final LayoutInflater inflater;
    private OnItemSelectedListener listener;
    private Cursor cursor;

    @Inject
    MainAdapter(@AppContext final Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void swapCursor(final Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    public void setListener(final OnItemSelectedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = inflater.inflate(R.layout.item_main, parent, false);
        return new MainHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainHolder holder, final int position) {
        cursor.moveToPosition(position);
        holder.bind(cursor);
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    static class  MainHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnItemSelectedListener listener;

        private final ImageView resultView;
        private final TextView countView;

        private int result = -1;

        MainHolder(final View itemView, final OnItemSelectedListener listener) {
            super(itemView);
            this.listener = listener;

            resultView = itemView.findViewById(R.id.result);
            countView = itemView.findViewById(R.id.count);
            itemView.findViewById(R.id.container).setOnClickListener(this);
        }

        void bind(final Cursor cursor) {
            result = cursor.getInt(cursor.getColumnIndexOrThrow("result"));
            if (result == 0) {
                resultView.setImageResource(R.drawable.baseline_check_circle_black_24);
            } else {
                resultView.setImageResource(R.drawable.baseline_error_black_24);
            }
            final int count = cursor.getInt(cursor.getColumnIndexOrThrow("c"));
            countView.setText(Integer.toString(count));
        }

        @Override
        public void onClick(final View v) {
            final int viewId = v.getId();
            if (viewId == R.id.container && listener != null) {
                listener.onItemSelected(result);
            }
        }
    }

    public interface OnItemSelectedListener {

        void onItemSelected(int result);
    }
}
