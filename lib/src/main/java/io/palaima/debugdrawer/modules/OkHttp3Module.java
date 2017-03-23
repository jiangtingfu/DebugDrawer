package io.palaima.debugdrawer.modules;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.format.Formatter;

import io.palaima.debugdrawer.BaseDebugModule;
import io.palaima.debugdrawer.DebugWidgets;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class OkHttp3Module extends BaseDebugModule {

    private final Cache cache;

    private Activity activity;

    public OkHttp3Module(@NonNull OkHttpClient client) {
        this.cache = client.cache();
    }

    @NonNull
    @Override
    public String getName() {
        return "OkHttp Cache";
    }

    @Override
    public void onCreate(Activity activity) {
        super.onCreate(activity);
        this.activity = activity;
    }

    @Override
    public DebugWidgets createWidgets(DebugWidgets.DebugWidgetsBuilder builder) {
        return builder.addText("Max Size", Formatter.formatFileSize(activity, cache.maxSize()))
                .addText("Write Errors", getWriteErrorCount())
                .addText("Request Count", cache.requestCount())
                .addText("Network Count", cache.networkCount())
                .addText("Hit Count", cache.hitCount())
                .build();
    }

    private String getWriteErrorCount() {
        int writeAbortCount = cache.writeAbortCount();
        int writeTotal = cache.writeSuccessCount() + writeAbortCount;
        int percentage = (int) ((1f * writeAbortCount / writeTotal) * 100);
        return String.valueOf(writeAbortCount) + " / " + writeTotal + " (" + percentage + "%)";
    }

}
