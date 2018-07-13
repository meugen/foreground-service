package meugeninua.foregroundservice.ui.activities.base.fragments.base.binding;

import android.view.View;

public interface Binding {

    void attachView(View view);

    void detachView();

    <V extends View> V get(int id);

    boolean has(int id);
}
