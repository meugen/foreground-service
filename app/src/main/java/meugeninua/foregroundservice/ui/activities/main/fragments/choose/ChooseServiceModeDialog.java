package meugeninua.foregroundservice.ui.activities.main.fragments.choose;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import javax.inject.Inject;

import meugeninua.foregroundservice.R;
import meugeninua.foregroundservice.app.di.qualifiers.ActivityContext;
import meugeninua.foregroundservice.app.managers.AppEventsManager;
import meugeninua.foregroundservice.app.managers.events.ServiceModeChosenEvent;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.BaseDialogFragment;
import meugeninua.foregroundservice.ui.activities.base.fragments.base.binding.Binding;

/**
 * @author meugen
 */
public class ChooseServiceModeDialog extends BaseDialogFragment<Binding>
        implements DialogInterface.OnClickListener {

    @Inject @ActivityContext Context context;
    @Inject AppEventsManager eventsManager;

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.message_choose_service_mode);
        builder.setPositiveButton(R.string.button_choose_mode_background, this);
        builder.setNegativeButton(R.string.button_choose_mode_foreground, this);
        return builder.create();
    }

    @Override
    public void onClick(final DialogInterface dialog, final int which) {
        if (which == AlertDialog.BUTTON_POSITIVE) {
            eventsManager.post(new ServiceModeChosenEvent(ServiceModeChosenEvent.BACKGROUND));
        } else if (which == AlertDialog.BUTTON_NEGATIVE) {
            eventsManager.post(new ServiceModeChosenEvent(ServiceModeChosenEvent.FOREGROUND));
        }
    }
}
