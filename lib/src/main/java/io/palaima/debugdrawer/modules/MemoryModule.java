package io.palaima.debugdrawer.modules;

import android.support.annotation.NonNull;
import android.text.format.Formatter;

import io.palaima.debugdrawer.BaseDebugModule;
import io.palaima.debugdrawer.DebugWidgets;

/**
 * @author Kale
 * @date 2017/3/22
 */
public class MemoryModule extends BaseDebugModule{

    @NonNull
    @Override
    public String getName() {
        return "Memory";
    }

    @Override
    public DebugWidgets createWidgets(DebugWidgets.DebugWidgetsBuilder builder) {
        //获取系统分配给应用的总内存大小
        String size = Formatter.formatFileSize(getActivity(), Runtime.getRuntime().totalMemory());
        return builder.addText("Total Memory", size).build();
    }
    
}
