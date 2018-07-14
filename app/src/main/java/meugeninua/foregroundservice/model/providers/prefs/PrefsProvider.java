package meugeninua.foregroundservice.model.providers.prefs;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class PrefsProvider extends ContentProvider implements PrefsProviderConstants {

    private static final int INTS_MATCH = 1;
    private static final int STRINGS_MATCH = 2;
    private static final int BOOLS_MATCH = 3;

    private UriMatcher matcher;

    @Inject SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        AndroidInjection.inject(this);

        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "ints", INTS_MATCH);
        matcher.addURI(AUTHORITY, "strings", STRINGS_MATCH);
        matcher.addURI(AUTHORITY, "bools", BOOLS_MATCH);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(
            @NonNull final Uri uri,
            @Nullable final String[] projection,
            @Nullable final String selection,
            @Nullable final String[] selectionArgs,
            @Nullable final String sortOrder) {
        Cursor cursor;

        final int code = matcher.match(uri);
        if (code == INTS_MATCH) {
            cursor = database.query("int_prefs",
                    projection, selection, selectionArgs,
                    null, null, sortOrder);
        } else if (code == STRINGS_MATCH) {
            cursor = database.query("string_prefs",
                    projection, selection, selectionArgs,
                    null, null, sortOrder);
        } else if (code == BOOLS_MATCH) {
            cursor = database.query("bool_prefs",
                    projection, selection, selectionArgs,
                    null, null, sortOrder);
        } else {
            throw new IllegalArgumentException("Not implemented yet");
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull final Uri uri) {
        String type;

        final int code = matcher.match(uri);
        if (code == INTS_MATCH) {
            type = "int/pref";
        } else if (code == STRINGS_MATCH) {
            type = "string/pref";
        } else if (code == BOOLS_MATCH) {
            type = "bool/pref";
        } else {
            throw new IllegalArgumentException("Not implemented yet");
        }
        return type;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull final Uri uri, @Nullable final ContentValues values) {
        Uri resultUri;

        final int code = matcher.match(uri);
        if (code == INTS_MATCH) {
            final long id = database.insertWithOnConflict(
                    "int_prefs", null, values,
                    SQLiteDatabase.CONFLICT_REPLACE);
            resultUri = INTS_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        } else if (code == STRINGS_MATCH) {
            final long id = database.insertWithOnConflict(
                    "string_prefs", null, values,
                    SQLiteDatabase.CONFLICT_REPLACE);
            resultUri = STRINGS_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        } else if (code == BOOLS_MATCH) {
            final long id = database.insertWithOnConflict(
                    "bool_prefs", null, values,
                    SQLiteDatabase.CONFLICT_REPLACE);
            resultUri = BOOLS_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        } else {
            throw new IllegalArgumentException("Not implemented yet");
        }
        return resultUri;
    }

    @Override
    public int delete(
            @NonNull final Uri uri,
            @Nullable final String selection,
            @Nullable final String[] selectionArgs) {
        final int count;

        final int code = matcher.match(uri);
        if (code == INTS_MATCH) {
            count = database.delete("int_prefs", selection, selectionArgs);
        } else if (code == STRINGS_MATCH) {
            count = database.delete("string_prefs", selection, selectionArgs);
        } else if (code == BOOLS_MATCH) {
            count = database.delete("bool_prefs", selection, selectionArgs);
        } else {
            throw new IllegalArgumentException("Not implemented yet");
        }
        return count;
    }

    @Override
    public int update(
            @NonNull final Uri uri,
            @Nullable final ContentValues values,
            @Nullable final String selection,
            @Nullable final String[] selectionArgs) {
        int count;

        final int code = matcher.match(uri);
        if (code == INTS_MATCH) {
            count = database.update("int_prefs", values, selection, selectionArgs);
        } else if (code == STRINGS_MATCH) {
            count = database.update("string_prefs", values, selection, selectionArgs);
        } else if (code == BOOLS_MATCH) {
            count = database.update("bool_prefs", values, selection, selectionArgs);
        } else {
            throw new IllegalArgumentException("Not implemented yet");
        }
        return count;
    }
}
