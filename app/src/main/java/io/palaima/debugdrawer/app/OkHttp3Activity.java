package io.palaima.debugdrawer.app;

import java.io.IOException;

import android.os.Bundle;
import android.support.annotation.Nullable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Kale
 * @date 2017/3/22
 */
public class OkHttp3Activity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okhttp_activity);
        sendRequest();
    }

    public static void sendRequest() {
        Request request = new Request.Builder().url("http://www.baidu.com/").build();
        MyApplication.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }
}
