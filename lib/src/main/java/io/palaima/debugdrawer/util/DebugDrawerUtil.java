package io.palaima.debugdrawer.util;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.support.annotation.CheckResult;

/**
 * @author Kale
 * @date 2016/5/2
 */
/*package*/public class DebugDrawerUtil {

    public static
    @CheckResult
    boolean hasClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Queries on-device packages for a handler for the supplied {@link Intent}.
     */
    public static boolean hasHandler(Context context, Intent intent) {
        List<ResolveInfo> handlers = context.getPackageManager().queryIntentActivities(intent, 0);
        return !handlers.isEmpty();
    }

    public static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences("debug_drawer_sp", Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getSpEditor(Context context) {
        return context.getSharedPreferences("debug_drawer_sp", Context.MODE_PRIVATE).edit();
    }
}
