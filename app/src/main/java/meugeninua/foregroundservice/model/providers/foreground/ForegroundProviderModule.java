package meugeninua.foregroundservice.model.providers.foreground;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import dagger.Module;
import dagger.Provides;
import meugeninua.foregroundservice.app.di.qualifiers.AppContext;
import meugeninua.foregroundservice.app.di.scopes.PerProvider;
import meugeninua.foregroundservice.model.db.AppDbOpenHelper;

@Module
public interface ForegroundProviderModule {

    @Provides @PerProvider
    static SQLiteDatabase bindForegroundOpenHelper(@AppContext final Context context) {
        return AppDbOpenHelper.foreground(context).getWritableDatabase();
    }

    @Provides @PerProvider
    static ContentResolver provideContentResolver(
            @AppContext final Context context) {
        return context.getContentResolver();
    }
}
