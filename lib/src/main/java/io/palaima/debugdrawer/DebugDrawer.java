package io.palaima.debugdrawer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.v4.util.ArrayMap;
import android.view.ViewGroup;

import com.amitshekhar.DebugDB;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.github.simonpercic.oklog.core.CustomLogManager;
import com.github.simonpercic.oklog.core.LogDataConfig;
import com.github.simonpercic.oklog3.OkLogInterceptor;
import com.hugo.watcher.Watcher;
import com.squareup.leakcanary.LeakCanary;

import io.palaima.debugdrawer.util.DebugDrawerUtil;
import io.palaima.debugdrawer.util.PackageManagerHook;
import okhttp3.OkHttpClient;

/**
 * @author Kale
 * @date 2017/5/9
 */
public class DebugDrawer {

    private static Map<Activity, DebugWidgetsFrame> sMap = new ArrayMap<>();

    public static OkHttpClient createOkLogHttpClient(OkHttpClient client) {
        if (!DebugDrawerUtil.hasClass("com.github.simonpercic.oklog3.OkLogInterceptor")) {
            return client;
        }

        // create an instance of OkLogInterceptor using a builder()
        OkLogInterceptor.Builder builder = OkLogInterceptor.builder();
        OkLogInterceptor okLogInterceptor = null;
        try {
            Method buildLogDataConfig = builder.getClass().getSuperclass().getDeclaredMethod("buildLogDataConfig");
            buildLogDataConfig.setAccessible(true);
            LogDataConfig cfg = (LogDataConfig) buildLogDataConfig.invoke(builder);
            CustomLogManager customLogManager = new CustomLogManager("http://responseecho-simonpercic.rhcloud.com", null, true, true, false, cfg);
            okLogInterceptor = builder.build();
            Field logManager = okLogInterceptor.getClass().getDeclaredField("logManager");
            logManager.setAccessible(true);
            logManager.set(okLogInterceptor, customLogManager);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return client.newBuilder()
                .addInterceptor(okLogInterceptor)
                .build();
    }

    public static void init(Application application, final Config cfg) {
        PackageManagerHook.hook(application); // hook pkgInfo

        if (DebugDrawerUtil.hasClass("com.amitshekhar.DebugDB")) {
            DebugDB.initialize(application);
        }
        if (DebugDrawerUtil.hasClass("com.hugo.watcher.Watcher")) {
            Watcher.getInstance().start(application);
            Watcher.getInstance().stop(application);
        }
        if (DebugDrawerUtil.hasClass("com.squareup.leakcanary.LeakCanary")
                && !LeakCanary.isInAnalyzerProcess(application)) {
            LeakCanary.install(application);
        }
        if (DebugDrawerUtil.hasClass("com.github.moduth.blockcanary.BlockCanary")) {
            BlockCanary.install(application, new BlockCanaryContext() {
                public int provideBlockThreshold() {
                    return 500;
                }

                public boolean displayNotification() {
                    return true;
                }
            }).start();
        }

        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                DebugWidgetsFrame widgetsLayout = sMap.get(activity);
                if (widgetsLayout == null && checkActivity(activity)) {
                    DebugWidgetsFrame layout = new DebugWidgetsFrame(activity, cfg.getModules());
                    sMap.put(activity, layout);
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {
                sMap.get(activity).resume();
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                sMap.get(activity).destroy();
                sMap.remove(activity);
            }
        });
    }

    @CheckResult
    public static boolean checkActivity(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.findViewById(android.R.id.content);
        return rootView != null && rootView.getChildCount() != 0;
    }

    /**
     * Open the drawer
     */
    public static void openDrawer(Activity currentAct) {
        sMap.get(currentAct).openDrawer();
    }

    /**
     * close the drawer
     */
    public static void closeDrawer(Activity currentAct) {
        sMap.get(currentAct).closeDrawer();
    }

    /**
     * Get the current state of the drawer.
     * True if the drawer is currently open.
     */
    public static boolean isDrawerOpened(Activity currentAct) {
        return sMap.get(currentAct).isDrawerOpened();
    }

    public abstract static class Config {

        protected abstract List<BaseDebugModule> getModules();

    }
}
