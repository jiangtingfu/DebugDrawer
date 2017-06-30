package io.palaima.debugdrawer.app;

import java.io.File;
import java.util.concurrent.TimeUnit;

import android.app.Application;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * @author Niklas Baudy (https://github.com/vanniktech)
 * @since 01/07/15
 */
public class MyApplication extends Application {

    public static OkHttpClient okHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        okHttpClient = createOkHttpClientBuilder(this).build();
        initDebugDrawer(okHttpClient);
    }

    protected void initDebugDrawer(final OkHttpClient client) {
        // empty
    }

    private static final int DISK_CACHE_SIZE = 30 * 1024 * 1024; // 30 MB

    private static OkHttpClient.Builder createOkHttpClientBuilder(Application app) {
        // Install an HTTP cache in the application cache directory.
        File cacheDir = new File(app.getCacheDir(), "okhttp3-cache");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

        return new OkHttpClient.Builder()
                .cache(cache)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS);
    }
}
