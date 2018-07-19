package meugeninua.foregroundservice.ui.activities.main.fragments.main.view;

import meugeninua.foregroundservice.ui.activities.main.fragments.main.adapters.MainAdapter;

public interface ArchMainView extends MainAdapter.OnItemSelectedListener {

    void onStartClick();

    void onStopClick();

    void onMoveForeground();

    void onMoveBackground();
}
