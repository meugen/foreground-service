package meugeninua.foregroundservice.model.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppDbOpenHelper extends SQLiteOpenHelper {

    private static final String FOREGROUND_NAME = "foreground";
    private static final int FOREGROUND_VERSION = 1;

    private static final String PREFS_NAME = "prefs";
    private static final int PREFS_VERSION = 1;

    public static AppDbOpenHelper foreground(final Context context) {
        return new AppDbOpenHelper(context, FOREGROUND_NAME, FOREGROUND_VERSION);
    }

    public static AppDbOpenHelper prefs(final Context context) {
        return new AppDbOpenHelper(context, PREFS_NAME, PREFS_VERSION);
    }

    private final AssetManager assets;
    private final String name;
    private final int version;

    private Pattern pattern;

    private AppDbOpenHelper(
            final Context context,
            final String name,
            final int version) {
        super(context, name, null, version);

        this.assets = context.getAssets();
        this.name = name;
        this.version = version;
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        onUpgrade(db, 0, version);
    }

    @Override
    public void onUpgrade(
            final SQLiteDatabase db,
            final int oldVersion,
            final int newVersion) {
        this.pattern = Pattern.compile("\\s*([^;]+);");
        try {
            for (int version = oldVersion + 1; version <= newVersion; version++) {
                upgrade(db, version);
            }
        } finally {
            this.pattern = null;
        }
    }

    private void upgrade(final SQLiteDatabase db, final int version) {
        final CharSequence content = fetchSql("db/" + name + "/" + version + ".sql");
        final Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            db.execSQL(matcher.group(1));
        }
    }

    private CharSequence fetchSql(final String name) {
        try {
            Reader reader = null;
            try {
                final InputStream stream = assets.open(name);
                reader = new InputStreamReader(stream);

                final StringBuilder builder = new StringBuilder();
                final char[] buf = new char[256];
                while (true) {
                    final int count = reader.read(buf);
                    if (count < 0) {
                        break;
                    }
                    builder.append(buf, 0, count);
                }
                return builder;
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
