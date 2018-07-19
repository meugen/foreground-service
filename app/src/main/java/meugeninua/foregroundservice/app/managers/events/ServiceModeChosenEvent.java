package meugeninua.foregroundservice.app.managers.events;

import android.support.annotation.IntDef;

/**
 * @author meugen
 */
public class ServiceModeChosenEvent {

    @IntDef({BACKGROUND,FOREGROUND})
    public @interface ServiceMode {}
    public static final int BACKGROUND = 1;
    public static final int FOREGROUND = 2;

    @ServiceMode
    public final int mode;

    public ServiceModeChosenEvent(final int mode) {
        this.mode = mode;
    }
}
