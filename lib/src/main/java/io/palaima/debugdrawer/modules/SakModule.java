package io.palaima.debugdrawer.modules;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.wanjian.sak.SAK;
import com.wanjian.sak.config.SakConfigBuild;

import io.palaima.debugdrawer.BaseDebugModule;
import io.palaima.debugdrawer.DebugWidgetStore;
import io.palaima.debugdrawer.util.DebugDrawerUtil;
import io.palaima.debugdrawer.view.EmptyActivity;

/**
 * @author Kale
 * @date 2017/6/28
 */
public class SakModule extends BaseDebugModule {

    public SakModule() {
        if (!DebugDrawerUtil.hasClass("com.wanjian.sak.SAK")) {
            throw new RuntimeException("SAK dependency is not found");
        }
    }

    @NonNull
    @Override
    public String getName() {
        return "Swiss Army Knife";
    }

    @Override
    protected DebugWidgetStore createWidgetStore(DebugWidgetStore.Builder builder) {
        return builder.addSwitch("Running", false, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new Handler().postDelayed(new Runnable(){
                        public void run() {
                            getActivity().startActivity(new Intent(getActivity(), EmptyActivity.class));
                        }
                    }, 300);
                    // 新开activity后生效
                    Toast.makeText(getActivity(), "Success~~~!", Toast.LENGTH_SHORT).show();
                    SAK.init(getActivity().getApplication(), new SakConfigBuild(getActivity()).build());
                } else {
                    SAK.unInstall(getActivity().getApplication());
                }
            }
        }).build();
    }
}
