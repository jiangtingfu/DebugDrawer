package io.palaima.debugdrawer.debug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.palaima.debugdrawer.BaseDebugModule;
import io.palaima.debugdrawer.DebugDrawer;
import io.palaima.debugdrawer.app.MyApplication;
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
import io.palaima.debugdrawer.modules.SakModule;
import io.palaima.debugdrawer.modules.SettingsModule;
import okhttp3.OkHttpClient;

/**
 * @author Kale
 * @date 2017/6/30
 */
public class DebugApplication extends MyApplication {

    @Override
    protected void initDebugDrawer(final OkHttpClient client) {
        super.initDebugDrawer(client);
        okHttpClient = DebugDrawer.createOkLogHttpClient(client); // 依赖于：com.github.simonpercic:oklog3:2.1.0

        DebugDrawer.init(this, new DebugDrawer.Config() {
            @Override
            protected List<BaseDebugModule> getModules() {
                return new ArrayList<>(Arrays.asList(
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
                        new SettingsModule(),
                        new SakModule() // 依赖于：com.wanjian:sak:0.1.2.8
                ));
            }
        });
    }
}
