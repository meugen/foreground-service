package meugeninua.foregroundservice.ui.activities.main.fragments.main.binding;

import android.database.Cursor;

import meugeninua.foregroundservice.ui.activities.base.fragments.base.binding.Binding;
import meugeninua.foregroundservice.ui.activities.main.fragments.main.view.ArchMainView;

public interface MainBinding extends Binding {

    void setupRecycler();

    void setupListeners(ArchMainView view);

    void setupCursor(Cursor cursor);

    void enableButtons();
}
