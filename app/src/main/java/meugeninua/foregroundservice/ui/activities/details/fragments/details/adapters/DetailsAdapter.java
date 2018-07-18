package meugeninua.foregroundservice.ui.activities.details.fragments.details.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import javax.inject.Inject;

import meugeninua.foregroundservice.R;
import meugeninua.foregroundservice.app.di.qualifiers.ActivityContext;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsHolder> {

    private final LayoutInflater inflater;
    private Cursor cursor;

    @Inject
    DetailsAdapter(@ActivityContext final Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void swapCursor(final Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DetailsHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = inflater.inflate(R.layout.item_details, parent, false);
        return new DetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailsHolder holder, final int position) {
        cursor.moveToPosition(position);
        holder.bind(cursor);
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    static class DetailsHolder extends RecyclerView.ViewHolder {

        private static final DateFormat FORMAT = DateFormat
                .getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);

        private final ImageView resultView;
        private final TextView timestampView;
        private final TextView messageView;

        DetailsHolder(final View itemView) {
            super(itemView);

            resultView = itemView.findViewById(R.id.result);
            timestampView = itemView.findViewById(R.id.timestamp);
            messageView = itemView.findViewById(R.id.message);
        }

        void bind(final Cursor cursor) {
            final int result = cursor.getInt(cursor.getColumnIndexOrThrow("result"));
            if (result == 0) {
                resultView.setImageResource(R.drawable.baseline_check_circle_black_24);
            } else {
                resultView.setImageResource(R.drawable.baseline_error_black_24);
            }
            timestampView.setText(FORMAT.format(new Date(cursor.getLong(cursor.getColumnIndexOrThrow("timestamp")))));
            messageView.setText(cursor.getString(cursor.getColumnIndexOrThrow("message")));
        }
    }
}
