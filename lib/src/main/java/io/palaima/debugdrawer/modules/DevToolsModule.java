package io.palaima.debugdrawer.modules;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import io.palaima.debugdrawer.BaseDebugModule;
import io.palaima.debugdrawer.DebugWidgetStore;
import io.palaima.debugdrawer.util.DebugDrawerUtil;

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
    public void onAttachActivity(Activity activity) {
        super.onAttachActivity(activity);
        this.activity = activity;
    }

    @Override
    public DebugWidgetStore createWidgetStore(DebugWidgetStore.Builder builder) {
        return builder.addButton("Jump to Dev-Tools", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                String pkgName = "cn.trinea.android.developertools";
                ComponentName cn = new ComponentName(pkgName, pkgName + ".MainActivity");
                intent.setComponent(cn);

                if (DebugDrawerUtil.hasHandler(getActivity(), intent)) {
                    activity.startActivity(intent);
                } else {
                    Toast.makeText(activity, "本功能需要下载外部应用", Toast.LENGTH_SHORT).show();
                    Uri uri = Uri.parse("http://app.mi.com/details?id=cn.trinea.android.developertools");
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            }
        }).build();
    }

}
