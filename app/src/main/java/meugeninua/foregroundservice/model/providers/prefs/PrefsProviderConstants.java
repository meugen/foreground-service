package meugeninua.foregroundservice.model.providers.prefs;

import android.net.Uri;

public interface PrefsProviderConstants {

    String AUTHORITY = "meugeninua.foregroundservice.provider.prefs";

    Uri BASE_URI = new Uri.Builder()
            .scheme("content")
            .authority(AUTHORITY)
            .build();

    Uri INTS_URI = BASE_URI.buildUpon()
            .appendPath("ints")
            .build();

    Uri STRINGS_URI = BASE_URI.buildUpon()
            .appendPath("strings")
            .build();

    Uri BOOLS_URI = BASE_URI.buildUpon()
            .appendPath("bools")
            .build();
}
