package meugeninua.foregroundservice;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class ForegroundProvider extends ContentProvider {

    static final String AUTHORITY = "meugeninua.foregroundservice.provider";
    private static final int STATS_MATCH = 1;
    private static final int REQUESTS_MATCH = 2;

    private SQLiteDatabase database;
    private UriMatcher matcher;
    private ContentResolver resolver;
    private Uri baseUri;

    @Override
    public boolean onCreate() {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "/stats", STATS_MATCH);
        matcher.addURI(AUTHORITY, "/requests", REQUESTS_MATCH);

        final Context context = getContext();
        resolver = context.getContentResolver();
        database = new DbOpenHelper(context).getWritableDatabase();
        baseUri = new Uri.Builder()
                .scheme("content")
                .authority(AUTHORITY)
                .build();
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
            resolver.notifyChange(baseUri.buildUpon().appendPath("stats").build(), null);
            resolver.notifyChange(baseUri.buildUpon().appendPath("requests").build(), null);
            return baseUri.buildUpon()
                    .appendPath("requests")
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
