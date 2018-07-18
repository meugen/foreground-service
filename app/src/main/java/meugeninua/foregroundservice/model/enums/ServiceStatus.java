package meugeninua.foregroundservice.model.enums;

import android.support.annotation.IntDef;

/**
 * @author meugen
 */
@IntDef({ServiceStatus.SERVICE_FOREGROUND,
        ServiceStatus.SERVICE_BACKGROUND,
        ServiceStatus.SERVICE_STOPPED})
public @interface ServiceStatus {

    int SERVICE_FOREGROUND = 1;
    int SERVICE_BACKGROUND = 2;
    int SERVICE_STOPPED = 3;
}
