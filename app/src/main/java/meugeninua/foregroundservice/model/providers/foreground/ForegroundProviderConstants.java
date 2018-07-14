package meugeninua.foregroundservice.model.providers.foreground;

import android.net.Uri;

public interface ForegroundProviderConstants {

    String AUTHORITY = "meugeninua.foregroundservice.provider.foreground";

    Uri BASE_URI = new Uri.Builder()
            .scheme("content")
            .authority(AUTHORITY)
            .build();

    Uri STATS_URI = BASE_URI.buildUpon()
            .appendPath("stats")
            .build();

    Uri REQUESTS_URI = BASE_URI.buildUpon()
            .appendPath("requests")
            .build();
}
