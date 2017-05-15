package io.palaima.debugdrawer.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Application;

import io.palaima.debugdrawer.DebugDrawer;
import io.palaima.debugdrawer.IDebugModule;
import io.palaima.debugdrawer.modules.ActivityModule;
import io.palaima.debugdrawer.modules.BuildModule;
import io.palaima.debugdrawer.modules.DataBaseModule;
import io.palaima.debugdrawer.modules.DevToolsModule;
import io.palaima.debugdrawer.modules.DeviceModule;
import io.palaima.debugdrawer.modules.LogcatModule;
import io.palaima.debugdrawer.modules.MemoryModule;
import io.palaima.debugdrawer.modules.MonitorModule;
import io.palaima.debugdrawer.modules.NetworkModule;
import io.palaima.debugdrawer.modules.OkHttp3Module;
import io.palaima.debugdrawer.modules.SettingsModule;
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
//        SAK.init(this, new SakConfigBuild(this).build());
        okHttpClient = createOkHttpClientBuilder(this).build();
        okHttpClient = DebugDrawer.createOkLogHttpClient(okHttpClient);
        initDebugDrawer();
    }

    private void initDebugDrawer() {
        DebugDrawer.init(this, new DebugDrawer.Config() {
            @Override
            protected List<IDebugModule> getModules() {
                return new ArrayList<IDebugModule>(Arrays.asList(
                        new ActivityModule(),
                        new MonitorModule(),
                        new MemoryModule(),
                        new CustomDevModule(),
                        new DevToolsModule(),
                        new BuildModule(),
                        new NetworkModule(),
                        new OkHttp3Module(okHttpClient),
                        new DataBaseModule(),
                        new LogcatModule(),
                        new DeviceModule(),
                        new SettingsModule()
                ));
            }
        });
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
