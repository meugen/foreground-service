package meugeninua.foregroundservice.app.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import javax.inject.Inject;

import meugeninua.foregroundservice.app.di.qualifiers.AppContext;
import meugeninua.foregroundservice.model.providers.prefs.PrefsProviderConstants;

public class SharedPrefsImpl implements SharedPrefs, PrefsProviderConstants {

    private static final int FALSE = 0;
    private static final int TRUE = 1;

    private final ContentResolver resolver;

    @Inject
    SharedPrefsImpl(@AppContext final Context context) {
        this.resolver = context.getContentResolver();
    }

    @Override
    public String getString(final String name, final String def) {
        Cursor cursor = null;
        try {
            cursor = resolver.query(STRINGS_URI,
                    new String[] {"val"},
                    "name=?",
                    new String[] {name},
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return def;
    }

    @Override
    public int getInt(final String name, final int def) {
        Cursor cursor = null;
        try {
            cursor = resolver.query(INTS_URI,
                    new String[] {"val"},
                    "name=?",
                    new String[] {name},
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return def;
    }

    @Override
    public boolean getBool(final String name, final boolean def) {
        Cursor cursor = null;
        try {
            cursor = resolver.query(BOOLS_URI,
                    new String[] {"val"},
                    "name=?",
                    new String[] {name},
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int val = cursor.getInt(0);
                return val == TRUE;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return def;
    }

    @Override
    public void putString(final String name, final String val) {
        final ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("val", val);
        resolver.insert(STRINGS_URI, values);
    }

    @Override
    public void putInt(final String name, final int val) {
        final ContentValues values = new ContentValues();
        values.put("name", name);
        values.put(name, val);
        resolver.insert(INTS_URI, values);
    }

    @Override
    public void putBool(final String name, final boolean val) {
        final ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("val", val ? TRUE : FALSE);
        resolver.insert(BOOLS_URI, values);
    }
}
