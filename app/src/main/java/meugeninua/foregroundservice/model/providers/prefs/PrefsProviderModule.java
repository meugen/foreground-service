package meugeninua.foregroundservice.model.providers.prefs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import dagger.Module;
import dagger.Provides;
import meugeninua.foregroundservice.app.di.qualifiers.AppContext;
import meugeninua.foregroundservice.app.di.scopes.PerProvider;
import meugeninua.foregroundservice.model.db.AppDbOpenHelper;

@Module
public interface PrefsProviderModule {

    @Provides @PerProvider
    static SQLiteDatabase providePrefsDatabase(@AppContext final Context context) {
        return AppDbOpenHelper.prefs(context).getWritableDatabase();
    }
}
