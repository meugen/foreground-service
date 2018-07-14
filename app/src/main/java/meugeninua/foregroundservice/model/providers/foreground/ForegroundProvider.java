package meugeninua.foregroundservice.model.providers.foreground;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class ForegroundProvider extends ContentProvider implements ForegroundProviderConstants {

    private static final int STATS_MATCH = 1;
    private static final int REQUESTS_MATCH = 2;

    @Inject SQLiteDatabase database;
    @Inject ContentResolver resolver;

    private UriMatcher matcher;

    @Override
    public boolean onCreate() {
        AndroidInjection.inject(this);

        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "/stats", STATS_MATCH);
        matcher.addURI(AUTHORITY, "/requests", REQUESTS_MATCH);
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        final int code = matcher.match(uri);
        if (code == STATS_MATCH) {
            return "stats/foreground";
        } else if (code == REQUESTS_MATCH) {
            return "requests/foreground";
        } else {
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int code = matcher.match(uri);
        if (code == REQUESTS_MATCH) {
            final long id = database.insertOrThrow("requests", null, values);
            resolver.notifyChange(STATS_URI, null);
            resolver.notifyChange(REQUESTS_URI, null);
            return REQUESTS_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        } else {
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;

        final int code = matcher.match(uri);
        if (code == STATS_MATCH) {
            cursor = database.rawQuery("SELECT result, count(id) c FROM requests GROUP BY result", new String[0]);
        } else if (code == REQUESTS_MATCH) {
            cursor = database.query("requests", projection, selection, selectionArgs, null, null, sortOrder);
        } else {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        cursor.setNotificationUri(resolver, uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
