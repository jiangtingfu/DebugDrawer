package io.palaima.debugdrawer.modules;

import java.text.DecimalFormat;

import android.support.annotation.NonNull;
import android.text.format.Formatter;

import io.palaima.debugdrawer.BaseDebugModule;
import io.palaima.debugdrawer.DebugWidgets;

/**
 * @author Kale
 * @date 2017/3/22
 */
public class MemoryModule extends BaseDebugModule {

    @NonNull
    @Override
    public String getName() {
        return "Memory";
    }

    @Override
    public DebugWidgets createWidgets(DebugWidgets.DebugWidgetsBuilder builder) {
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();

        DecimalFormat format = new DecimalFormat("0.00%");
        String percent = format.format((double) freeMemory / totalMemory);

        return builder.addText("Free/Total",
                Formatter.formatFileSize(getActivity(), freeMemory) + "/" + Formatter.formatFileSize(getActivity(), totalMemory) 
                        + " (" + percent + ")")
                .build();
    }

}
