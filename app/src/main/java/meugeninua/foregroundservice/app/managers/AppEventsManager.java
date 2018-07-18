package meugeninua.foregroundservice.app.managers;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.AnyThread;
import android.support.annotation.MainThread;
import android.support.v4.util.ArrayMap;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * @author meugen
 */
@Singleton
public class AppEventsManager {

    private final Map<UUID, ObserverWrapper<?>> observers;
    final Handler handler;

    @Inject
    AppEventsManager() {
        observers = new ArrayMap<>();
        handler = new Handler(Looper.getMainLooper());
    }

    @AnyThread
    public void post(final Object event) {
        Timber.d(Thread.currentThread().getName());

        final List<ObserverWrapper<?>> wrappers;
        synchronized (this) {
            wrappers = new ArrayList<>(observers.values());
        }
        for (ObserverWrapper<?> wrapper : wrappers) {
            wrapper.update(this, event);
        }
    }

    @MainThread
    public <T> void subscribeToEvent(
            final LifecycleOwner owner,
            final Class<T> clazz,
            final TypedObserver<T> observer) {
        final ObserverWrapper<T> impl = new ObserverWrapper<>();
        impl.observer = observer;
        impl.clazz = clazz;
        impl.ownerRef = new WeakReference<>(owner);
        impl.managerRef = new WeakReference<>(this);

        UUID key = null;
        synchronized (this) {
            while (key == null || observers.containsKey(key)) {
                key = UUID.randomUUID();
            }
            impl.key = key;
            observers.put(key, impl);
        }
        impl.attachToLifecycle();
    }

    @MainThread
    synchronized void unsubscribe(final UUID key) {
        Timber.d("Unsubscribed %s", key);
        final ObserverWrapper<?> wrapper = observers.remove(key);
        if (wrapper != null) {
            wrapper.detachFromLifecycle();
        }
    }

    private static class ObserverWrapper<T> implements GenericLifecycleObserver {

        Class<T> clazz;
        TypedObserver<T> observer;
        UUID key;
        WeakReference<LifecycleOwner> ownerRef;
        WeakReference<AppEventsManager> managerRef;

        @Override
        public void onStateChanged(
                final LifecycleOwner source,
                final Lifecycle.Event event) {
            final AppEventsManager manager = managerRef.get();
            if (manager != null && source.getLifecycle()
                    .getCurrentState() == Lifecycle.State.DESTROYED) {
                manager.unsubscribe(key);
            }
        }

        void attachToLifecycle() {
            final LifecycleOwner owner = ownerRef.get();
            if (owner != null) {
                owner.getLifecycle().addObserver(this);
            }
        }

        @MainThread
        void detachFromLifecycle() {
            final LifecycleOwner owner = ownerRef.get();
            if (owner != null) {
                owner.getLifecycle().removeObserver(this);
            }
        }

        @AnyThread
        void update(
                final AppEventsManager manager,
                final Object arg) {
            if (clazz.isInstance(arg)) {
                manager.handler.post(() -> observer.onUpdate(clazz.cast(arg)));
            }
        }
    }

    public interface TypedObserver<T> {

        @MainThread
        void onUpdate(T event);
    }
}
