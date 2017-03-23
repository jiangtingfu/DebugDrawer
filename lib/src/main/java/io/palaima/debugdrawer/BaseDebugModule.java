package io.palaima.debugdrawer;

import android.app.Activity;

/**
 * @author Kale
 * @date 2017/3/22
 */
public abstract class BaseDebugModule implements IDebugModule {

    private Activity activity;

    @Override
    public void onCreate(Activity activity) {

    }

    @Override
    public void onDrawerOpened() {

    }

    @Override
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

}
