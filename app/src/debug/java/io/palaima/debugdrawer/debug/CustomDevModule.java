package io.palaima.debugdrawer.debug;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import io.palaima.debugdrawer.BaseDebugModule;
import io.palaima.debugdrawer.DebugWidgetStore;
import io.palaima.debugdrawer.app.CustomDevActivity;

/**
 * @author Kale
 * @date 2017/5/10
 */
class CustomDevModule extends BaseDebugModule {

    @NonNull
    @Override
    public String getName() {
        return "自定义调试页面";
    }

    @Override
    public DebugWidgetStore createWidgetStore(DebugWidgetStore.Builder builder) {
        return builder.addButton("跳转到自定义调试页面", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), CustomDevActivity.class));
            }
        }).build();
    }
}
