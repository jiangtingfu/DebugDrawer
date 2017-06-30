package io.palaima.debugdrawer.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.palaima.debugdrawer.DebugDrawer;
import kale.debug.log.Logcat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logcat.startLogCatServer(this);

        findViewById(R.id.open_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DebugDrawer.openDrawer(MainActivity.this);
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
        findViewById(R.id.block_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BlockActivity.class));
            }
        });
    }

}
