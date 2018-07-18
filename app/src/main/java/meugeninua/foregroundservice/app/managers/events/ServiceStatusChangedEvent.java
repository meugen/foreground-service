package meugeninua.foregroundservice.app.managers.events;

import android.os.Parcel;
import android.os.Parcelable;

import meugeninua.foregroundservice.model.enums.ServiceStatus;

/**
 * @author meugen
 */
public class ServiceStatusChangedEvent implements Parcelable {

    public static final Creator<ServiceStatusChangedEvent> CREATOR
            = new Creator<ServiceStatusChangedEvent>() {

        @Override
        public ServiceStatusChangedEvent createFromParcel(final Parcel source) {
            final int newServiceStatus = source.readInt();
            return new ServiceStatusChangedEvent(newServiceStatus);
        }

        @Override
        public ServiceStatusChangedEvent[] newArray(final int size) {
            return new ServiceStatusChangedEvent[size];
        }
    };

    @ServiceStatus
    public final int newServiceStatus;

    public ServiceStatusChangedEvent(final int newServiceStatus) {
        this.newServiceStatus = newServiceStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(newServiceStatus);
    }
}
