package io.palaima.debugdrawer.modules;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.amitshekhar.DebugDB;

import io.palaima.debugdrawer.BaseDebugModule;
import io.palaima.debugdrawer.DebugWidgets;
import io.palaima.debugdrawer.util.LibUtil;

/**
 * @author Kale
 * @date 2017/3/22
 *
 * https://github.com/amitshekhariitbhu/Android-Debug-Database
 */
public class DataBaseModule extends BaseDebugModule {

    private Context context;

    public DataBaseModule() {
        if (!LibUtil.hasDependency("com.amitshekhar.DebugDB")) {
            throw new RuntimeException("DebugDB dependency is not found");
        }
    }

    @NonNull
    @Override
    public String getName() {
        return "DataBase";
    }

    @Override
    public void onCreate(Activity activity) {
        super.onCreate(activity);
        context = activity;
    }

    @Override
    public DebugWidgets createWidgets(DebugWidgets.DebugWidgetsBuilder builder) {
        DebugDB.getAddressLog();
        return builder
                .addSwitch("DB Debug", isDebug(), new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            DebugDB.initialize(context.getApplicationContext());
                        } else {
                            DebugDB.shutDown();
                        }
                    }
                })
                .addText("Address", getDebugAddress())
                .build();
    }

    @Override
    public void onDrawerOpened() {
        if (isDebug()) {
            showDebugDBAddressLogToast(context);
        }
    }

    public boolean isDebug() {
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

    private static void showDebugDBAddressLogToast(Context context) {
        try {
            Class<?> debugDB = Class.forName("com.amitshekhar.DebugDB");
            Method getAddressLog = debugDB.getMethod("getAddressLog");
            Object value = getAddressLog.invoke(null);
            Toast.makeText(context, (String) value, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * origin string:Open http://10.4.49.180:8080 in your browser
     * @return http://10.4.49.180:8080
     */
    private String getDebugAddress() {
        String address = DebugDB.getAddressLog();
        int start = address.indexOf("http://");
        int end = address.indexOf(" in your browser") + 1;
        return address.substring(start, end);
    }
}
