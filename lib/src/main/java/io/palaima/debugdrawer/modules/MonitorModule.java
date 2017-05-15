package io.palaima.debugdrawer.modules;

import java.lang.reflect.Field;

import android.support.annotation.NonNull;
import android.widget.CompoundButton;

import com.github.moduth.blockcanary.BlockCanary;
import com.hugo.watcher.Watcher;

import io.palaima.debugdrawer.BaseDebugModule;
import io.palaima.debugdrawer.DebugWidgets;
import io.palaima.debugdrawer.util.DebugDrawerUtil;

/**
 * @author Kale
 * @date 2017/5/11
 *
 * https://github.com/xcc3641/Watcher
 */
public class MonitorModule extends BaseDebugModule {

    @NonNull
    @Override
    public String getName() {
        return "Monitor";
    }

    @Override
    public DebugWidgets createWidgets(DebugWidgets.DebugWidgetsBuilder builder) {
        builder.addSwitch("Fps & Memory", isFpsFloatShown(), new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Watcher.getInstance().start(getActivity());
                } else {
                    Watcher.getInstance().stop(getActivity());
                }
            }
        });
        if (DebugDrawerUtil.hasClass("com.github.moduth.blockcanary.BlockCanary")) {
            builder.addSwitch("Block Canary", isBlockCanaryRunning(), new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        BlockCanary.get().start();
                    } else {
                        BlockCanary.get().stop();
                    }
                }
            });
        }
        return builder.build();
    }

    private boolean isFpsFloatShown() {
        try {
            Class<?> watcher = Class.forName("com.hugo.watcher.Watcher");
            Field hasStarted = watcher.getDeclaredField("mHasStarted");
            hasStarted.setAccessible(true);
            return hasStarted.getBoolean(Watcher.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isBlockCanaryRunning() {
        try {
            Class<?> watcher = Class.forName("com.github.moduth.blockcanary.BlockCanary");
            Field hasStarted = watcher.getDeclaredField("mMonitorStarted");
            hasStarted.setAccessible(true);
            return hasStarted.getBoolean(BlockCanary.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
