package io.palaima.debugdrawer.modules;

import java.lang.reflect.Field;

import android.support.annotation.NonNull;
import android.widget.CompoundButton;

import com.amitshekhar.DebugDB;

import io.palaima.debugdrawer.BaseDebugModule;
import io.palaima.debugdrawer.DebugWidgets;
import io.palaima.debugdrawer.util.DebugDrawerUtil;
import kale.debug.log.util.NetworkUtils;

/**
 * @author Kale
 * @date 2017/3/22
 *
 * https://github.com/amitshekhariitbhu/Android-Debug-Database
 */
public class DataBaseModule extends BaseDebugModule {

    public DataBaseModule() {
        if (!DebugDrawerUtil.hasClass("com.amitshekhar.DebugDB")) {
            throw new RuntimeException("DebugDB dependency is not found");
        }
    }

    @NonNull
    @Override
    public String getName() {
        return "DataBase";
    }

    @Override
    public DebugWidgets createWidgets(DebugWidgets.DebugWidgetsBuilder builder) {
        return builder
                .addSwitch("DB Server", isServerRunning(), new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean opened) {
                        if (opened) {
                            DebugDB.initialize(getActivity());
                        } else {
                            DebugDB.shutDown();
                        }
                    }
                })
                .addText("Address", NetworkUtils.getPhoneLocalIp(getActivity(), 8080))
                .build();
    }

    private boolean isServerRunning() {
        try {
            Class<?> debugDB = Class.forName("com.amitshekhar.DebugDB");
            Field server = debugDB.getDeclaredField("clientServer");
            if (server != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
