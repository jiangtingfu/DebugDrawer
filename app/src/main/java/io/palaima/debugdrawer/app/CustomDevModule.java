package io.palaima.debugdrawer.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import io.palaima.debugdrawer.BaseDebugModule;
import io.palaima.debugdrawer.DebugWidgets;

/**
 * @author Kale
 * @date 2017/5/10
 */
public class CustomDevModule extends BaseDebugModule {

    @NonNull
    @Override
    public String getName() {
        return "自定义调试页面";
    }

    @Override
    public DebugWidgets createWidgets(DebugWidgets.DebugWidgetsBuilder builder) {
        return builder.addButton("跳转到自定义调试页面", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), CustomDevActivity.class));
            }
        }).build();
    }
}
