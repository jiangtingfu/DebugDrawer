package io.palaima.debugdrawer.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.support.v7.app.AppCompatActivity;

import io.palaima.debugdrawer.BaseDebugModule;
import io.palaima.debugdrawer.DebugDrawer;
import io.palaima.debugdrawer.IDebugModule;
import io.palaima.debugdrawer.modules.ActivityModule;
import io.palaima.debugdrawer.modules.BuildModule;
import io.palaima.debugdrawer.modules.DataBaseModule;
import io.palaima.debugdrawer.modules.DevToolsModule;
import io.palaima.debugdrawer.modules.DeviceModule;
import io.palaima.debugdrawer.modules.LogcatModule;
import io.palaima.debugdrawer.modules.MemoryModule;
import io.palaima.debugdrawer.modules.NetworkModule;
import io.palaima.debugdrawer.modules.OkHttp3Module;
import io.palaima.debugdrawer.modules.SakModule;
import io.palaima.debugdrawer.modules.SettingsModule;

/**
 * @author Kale
 * @date 2017/3/22
 */
public class BaseActivity extends AppCompatActivity {

    DebugDrawer debugDrawer;

    @Override
    protected void onStart() {
        super.onStart();
        if (debugDrawer == null && DebugDrawer.checkActivity(this)) {
            debugDrawer = new DebugDrawer.Builder(this)
                    .modules(getDebugModules())
                    .build();
//            debugDrawer.openDrawer();
        }
    }

    public List<IDebugModule> getDebugModules() {
        List<BaseDebugModule> modules = Arrays.asList(
                new SakModule(),
                new ActivityModule(),
                new MemoryModule(),
                new DataBaseModule(),
                new BuildModule(),
                new NetworkModule(),
                new OkHttp3Module(MyApplication.okHttpClient),
                new DevToolsModule(),
                new LogcatModule(),
                new DeviceModule(),
                new SettingsModule()
        );
        return new ArrayList<IDebugModule>(modules);
    }
}
