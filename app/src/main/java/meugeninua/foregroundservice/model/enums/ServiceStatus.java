package meugeninua.foregroundservice.model.enums;

import android.support.annotation.IntDef;

/**
 * @author meugen
 */
@IntDef({ServiceStatus.SERVICE_FOREGROUND,
        ServiceStatus.SERVICE_BACKGROUND,
        ServiceStatus.SERVICE_STOPPED,
        ServiceStatus.SERVICE_INITIALIZED,
        ServiceStatus.SERVICE_VOID})
public @interface ServiceStatus {

    int MASK_CAN_BE_STARTED = 0x0100;
    int MASK_CAN_BE_STOPPED = 0x0200;
    int MASK_CAN_MOVE_FOREGROUND = 0x0400;
    int MASK_CAN_MOVE_BACKGROUND = 0x0800;

    int SERVICE_FOREGROUND = 0x01 | MASK_CAN_BE_STOPPED | MASK_CAN_MOVE_BACKGROUND;
    int SERVICE_BACKGROUND = 0x02 | MASK_CAN_BE_STOPPED | MASK_CAN_MOVE_FOREGROUND;
    int SERVICE_STOPPED = 0x03 | MASK_CAN_BE_STARTED;
    int SERVICE_INITIALIZED = 0x04 | MASK_CAN_BE_STARTED;
    int SERVICE_VOID = 0x05;
}
