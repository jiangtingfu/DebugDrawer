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
        okHttpClient = DebugDrawer.createOkLogHttpClient(okHttpClient); // 依赖于：com.github.simonpercic:oklog3:2.1.0
        DebugDrawer.init(this, new DebugDrawer.Config() {
            @Override
            protected List<IDebugModule> getModules() {
                return new ArrayList<IDebugModule>(Arrays.asList(
                        new ActivityModule(),
                        new MonitorModule(), // 依赖于：com.github.xcc3641:watcher:0.5
                        new MemoryModule(),
                        new CustomDevModule(), // 自定义的module
                        new DevToolsModule(),
                        new BuildModule(),
                        new NetworkModule(),
                        new OkHttp3Module(okHttpClient), // 依赖于：com.squareup.okhttp3:okhttp:3.1.2
                        new DataBaseModule(), // 依赖于：com.amitshekhar.android:debug-db:1.0.0
                        new LogcatModule(), // 依赖于：com.github.tianzhijiexian:Logcat:1.0.7
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
