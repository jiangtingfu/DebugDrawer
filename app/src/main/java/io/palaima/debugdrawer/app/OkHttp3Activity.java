package io.palaima.debugdrawer.app;

import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.simonpercic.oklog.core.RequestListActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Kale
 * @date 2017/3/22
 */
public class OkHttp3Activity extends AppCompatActivity {

    private static final String TAG = "OkHttp3Activity";
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okhttp_activity);

        findViewById(R.id.request_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OkHttp3Activity.this, RequestListActivity.class));
            }
        });

        sendRequest("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1");
        sendRequest("http://gank.io/api/random/data/%E7%A6%8F%E5%88%A9/20");
        sendRequest("http://gank.io/api/fail");
        sendRequest("http://ip.taobao.com/service/getIpInfo.php?ip=117.89.35.58");
        sendRequest("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=218.4.255.255");
    }

    private void sendRequest(String url) {
        Request request = new Request.Builder().url(url).build();
        MyApplication.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: ---- " + call.request().url());
            }
        });
    }

}
