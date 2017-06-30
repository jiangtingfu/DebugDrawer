package io.palaima.debugdrawer;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * @author Kale
 * @date 2017/3/22
 */
public abstract class BaseDebugModule {

    private Activity activity;

    protected void onDrawerOpened() {
    }

    @NonNull
    public abstract String getName();

    protected abstract DebugWidgetStore createWidgetStore(DebugWidgetStore.Builder builder);

    protected void onAttachActivity(Activity activity) {
    }

    protected void onActivityResume() {
    }

    protected void onActivityDestroy() {
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
        onAttachActivity(activity);
    }

    public Activity getActivity() {
        return activity;
    }

}
