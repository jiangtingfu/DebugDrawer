package io.palaima.debugdrawer.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.open_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                debugDrawer.openDrawer();
            }
        });
        findViewById(R.id.db_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DataBaseActivity.class));
            }
        });
        findViewById(R.id.leak_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LeakActivity.class));
            }
        });
        findViewById(R.id.okhttp_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OkHttp3Activity.class));
            }
        });
        findViewById(R.id.ui_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UiActivity.class));
            }
        });
        OkHttp3Activity.sendRequest();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        debugDrawer.destroy();
    }
}
