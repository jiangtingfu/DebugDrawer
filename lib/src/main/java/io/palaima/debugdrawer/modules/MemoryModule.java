package io.palaima.debugdrawer.modules;

import java.text.DecimalFormat;

import android.support.annotation.NonNull;
import android.text.format.Formatter;

import io.palaima.debugdrawer.BaseDebugModule;
import io.palaima.debugdrawer.DebugWidgetStore;

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
    public DebugWidgetStore createWidgetStore(DebugWidgetStore.Builder builder) {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();

        DecimalFormat format = new DecimalFormat("0.00%");
        String percent = format.format((double) totalMemory / maxMemory);

        return builder.addText("Total/Max",
                Formatter.formatFileSize(getActivity(), totalMemory) + "/" + Formatter.formatFileSize(getActivity(), maxMemory) 
                        + " (" + percent + ")")
                .build();
    }

}
