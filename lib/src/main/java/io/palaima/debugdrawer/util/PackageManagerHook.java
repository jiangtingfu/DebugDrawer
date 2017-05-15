package io.palaima.debugdrawer.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;


/**
 * https://github.com/AlanCheen/Climb/blob/master/app/src/main/java/me/yifeiyuan/climb/hook/PackageManagerHook.java
 *
 * @date 2017/5/8
 */
public class PackageManagerHook {

    public static final String TAG = "PkgManagerHook";

    public static final String KEY_CURRENT_VERSION_CODE = "key_current_version_code";

    public static final String KEY_CURRENT_VERSION_NAME = "key_current_version_name";

    /**
     * 越早 hook 越好，推荐在 attachBaseContext 调用
     *
     * @param context base
     */
    public static void hook(final Context context) {
        try {
            // 获取 activityThread
            Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread", false, context.getClassLoader());
            Method currentActivityThread = activityThreadClazz.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object activityThread = currentActivityThread.invoke(null);

            // 获取 activityThread 的 packageManager
            Method getPackageManager = activityThreadClazz.getDeclaredMethod("getPackageManager");
            getPackageManager.setAccessible(true);
            final Object pkgManager = getPackageManager.invoke(activityThread);//IPackageManager$Stub$Proxy

            // 动态代理
            Class<?> packageManagerClazz = Class.forName("android.content.pm.IPackageManager", false, context.getClassLoader());

            Object pmProxy = Proxy.newProxyInstance(context.getClassLoader(), new Class[]{packageManagerClazz}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Object result = method.invoke(pkgManager, args);

                    if ("getPackageInfo".equals(method.getName())) {
                        PackageInfo pkgInfo = (PackageInfo) result;

                        SharedPreferences sp = DebugDrawerUtil.getSp(context);
                        pkgInfo.versionCode = sp.getInt(KEY_CURRENT_VERSION_CODE, 0);
                        pkgInfo.versionName = sp.getString(KEY_CURRENT_VERSION_NAME, "1.0");
                    }
                    return result;
                }
            });

            //hook sPackageManager
            Field packageManagerField = activityThreadClazz.getDeclaredField("sPackageManager");
            packageManagerField.setAccessible(true);
            packageManagerField.set(activityThread, pmProxy);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
