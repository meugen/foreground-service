package meugeninua.foregroundservice.model.actions.fetch;

import android.content.ContentValues;
import android.content.Context;

import java.io.Reader;

import javax.inject.Inject;

import meugeninua.foregroundservice.app.di.qualifiers.AppContext;
import meugeninua.foregroundservice.model.actions.AppActionApi;
import meugeninua.foregroundservice.model.providers.foreground.ForegroundProviderConstants;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * @author meugen
 */
public class FetchActionApi implements AppActionApi {

    @Inject @AppContext Context context;
    @Inject OkHttpClient client;

    @Inject
    FetchActionApi() {}

    @Override
    public void onAction() {
        final Request request = new Request.Builder()
                .get().url("https://restapi.meugen.in.ua")
                .build();
        final Call call = client.newCall(request);
        try {
            final Response response = call.execute();

            final Reader reader = response.body().charStream();
            final StringBuilder content = new StringBuilder();

            final char[] buf = new char[256];
            while (true) {
                final int count = reader.read(buf);
                if (count < 0) {
                    break;
                }
                content.append(buf, 0, count);
            }
            Timber.d(content.toString());

            insertResult(response.isSuccessful() ? 0 : 1, response.message());
        } catch (Throwable e) {
            Timber.e(e);
            insertResult(1, e.getMessage());
        }
    }

    private void insertResult(final int result, final String message) {
        final ContentValues values = new ContentValues();
        values.put("timestamp", System.currentTimeMillis());
        values.put("result", result);
        values.put("message", message);
        context.getContentResolver().insert(ForegroundProviderConstants
                .REQUESTS_URI, values);
    }
}
