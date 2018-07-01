package meugeninua.foregroundservice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String NAME = "foreground.db";
    private static final int VERSION = 1;

    public DbOpenHelper(final Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL("CREATE TABLE requests (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " timestamp BIGINTEGER NOT NULL," +
                " result INTEGER NOT NULL," +
                " message VARCHAR(200) NOT NULL);");
    }

    @Override
    public void onUpgrade(
            final SQLiteDatabase db,
            final int oldVersion,
            final int newVersion) {
        db.execSQL("DROP TABLE requests;");
        onCreate(db);
    }
}
