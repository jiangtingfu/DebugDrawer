package io.palaima.debugdrawer.modules;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CompoundButton;

import io.palaima.debugdrawer.BaseDebugModule;
import io.palaima.debugdrawer.DebugWidgets;
import io.palaima.debugdrawer.util.DebugDrawerUtil;
import kale.debug.log.Logcat;
import kale.debug.log.ui.LogActivity;
import kale.debug.log.util.NetworkUtils;

/**
 * @author Kale
 * @date 2016/3/26
 */
public class LogcatModule extends BaseDebugModule {

    private Activity activity;

    public LogcatModule() {
        if (!DebugDrawerUtil.hasClass("kale.debug.log.ui.LogActivity")) {
            throw new RuntimeException("Logcat dependency is not found");
        }
    }

    @NonNull
    @Override
    public String getName() {
        return "Logcat";
    }

    @Override
    public void onAttachActivity(Activity activity) {
        super.onAttachActivity(activity);
        this.activity = activity;
    }

    @Override
    public DebugWidgets createWidgets(DebugWidgets.DebugWidgetsBuilder builder) {
        String address = NetworkUtils.getWebLogcatAddress(getActivity(), 8819);
        return builder
                .addSwitch("Logcat Server", Logcat.isServerRunning(), new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            Logcat.startLogCatServer(getActivity());
                        } else {
                            Logcat.shutDownServer();
                        }
                    }
                })
                .addText("Address", address)
                .addButton("Show App Log", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.startActivity(new Intent(activity, LogActivity.class));
                    }
                }).build();
    }

}
