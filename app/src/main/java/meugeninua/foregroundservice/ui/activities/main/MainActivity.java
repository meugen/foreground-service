package meugeninua.foregroundservice.ui.activities.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import meugeninua.foregroundservice.R;
import meugeninua.foregroundservice.ui.activities.base.BaseActivity;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.binding.Binding;

public class MainActivity extends BaseActivity<Binding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = binding
                .get(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
