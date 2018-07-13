package meugeninua.foregroundservice.model.provider;

import android.net.Uri;

public interface ProviderConstants {

    String AUTHORITY = "meugeninua.foregroundservice.provider";

    Uri BASE_URI = new Uri.Builder()
            .scheme("content")
            .authority(ForegroundProvider.AUTHORITY)
            .build();

    Uri STATS_URI = BASE_URI.buildUpon()
            .appendPath("stats")
            .build();

    Uri REQUESTS_URI = BASE_URI.buildUpon()
            .appendPath("requests")
            .build();
}
