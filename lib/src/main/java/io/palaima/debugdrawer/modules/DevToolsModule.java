package io.palaima.debugdrawer.modules;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import io.palaima.debugdrawer.BaseDebugModule;
import io.palaima.debugdrawer.DebugWidgets;

/**
 * @author Kale
 * @date 2017/3/22
 */
public class DevToolsModule extends BaseDebugModule {

    private Activity activity;

    @NonNull
    @Override
    public String getName() {
        return "Dev Tools";
    }

    @Override
    public void onCreate(Activity activity) {
        super.onCreate(activity);
        this.activity = activity;
    }

    @Override
    public DebugWidgets createWidgets(DebugWidgets.DebugWidgetsBuilder builder) {
        return builder.addButton("Jump to Dev-Tools", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                String pkgName = "cn.trinea.android.developertools";
                ComponentName cn = new ComponentName(pkgName, pkgName + ".MainActivity");
                intent.setComponent(cn);
                activity.startActivity(intent);
            }
        }).build();
    }

}
