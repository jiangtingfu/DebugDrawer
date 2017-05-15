package io.palaima.debugdrawer;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * @author Kale
 * @date 2016/4/30
 */
public interface IDebugModule {

    @NonNull
    String getName();

    void onDrawerOpened();

    void setActivity(Activity activity);

    void onAttachActivity(Activity activity);

    void onActivityResume();

    void onActivityDestroy();

    DebugWidgets createWidgets(DebugWidgets.DebugWidgetsBuilder builder);

}
