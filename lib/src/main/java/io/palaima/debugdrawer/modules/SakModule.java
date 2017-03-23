package io.palaima.debugdrawer.modules;

import android.app.Activity;
import android.support.annotation.NonNull;

import io.palaima.debugdrawer.BaseDebugModule;
import io.palaima.debugdrawer.DebugWidgets;
import io.palaima.debugdrawer.util.LibUtil;

/**
 * @author Kale
 * @date 2017/3/22
 */
public class SakModule extends BaseDebugModule {

    public SakModule() {
        if (!LibUtil.hasDependency("com.wanjian.sak.SAK")) {
            throw new RuntimeException("SAK dependency is not found");
        }
    }

    @NonNull
    @Override
    public String getName() {
        return "SAK";
    }

    @Override
    public void onCreate(Activity activity) {
        super.onCreate(activity);
//        SAK.init(activity.getApplication(), new SakConfigBuild(activity).build());
    }

    @Override
    public DebugWidgets createWidgets(DebugWidgets.DebugWidgetsBuilder builder) {
        return null;
    }

}
