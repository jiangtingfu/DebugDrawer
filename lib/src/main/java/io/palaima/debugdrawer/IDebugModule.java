package io.palaima.debugdrawer;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * @author Kale
 * @date 2016/4/30
 */
public interface IDebugModule {

    @NonNull String getName();

    void setActivity(Activity activity);

    void onCreate(Activity activity);
    
    DebugWidgets createWidgets(DebugWidgets.DebugWidgetsBuilder builder);

    void onDrawerOpened();

}
