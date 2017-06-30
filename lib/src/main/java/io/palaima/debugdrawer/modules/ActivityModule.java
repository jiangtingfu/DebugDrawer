package io.palaima.debugdrawer.modules;

import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.NonNull;

import io.palaima.debugdrawer.BaseDebugModule;
import io.palaima.debugdrawer.DebugWidgetStore;

/**
 * @author Kale
 * @date 2016/5/4
 */
public class ActivityModule extends BaseDebugModule {

    @NonNull
    @Override
    public String getName() {
        return "Current Activity";
    }

    @Override
    public DebugWidgetStore createWidgetStore(DebugWidgetStore.Builder builder) {
        String fullName = getRunningActivityName(getActivity());
        String[] names = fullName.split("\\.");
        return builder.addText(null, names[names.length - 1]).build();
    }

    /**
     * @return 手机当前的activity
     */
    public static String getRunningActivityName(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
    }

}
