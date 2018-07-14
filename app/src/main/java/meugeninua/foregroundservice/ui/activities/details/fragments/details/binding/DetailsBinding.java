package meugeninua.foregroundservice.ui.activities.details.fragments.details.binding;

import android.database.Cursor;

import meugeninua.foregroundservice.ui.activities.base.fragments.base.binding.Binding;

public interface DetailsBinding extends Binding {

    void setupRecycler();

    void setupCursor(Cursor cursor);
}
