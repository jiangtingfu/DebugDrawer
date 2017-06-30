package io.palaima.debugdrawer.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * @author Kale
 * @date 2017/5/10
 */
public class CustomDevActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);

        ((TextView) findViewById(R.id.content_tv)).setText("Custom Dev Page");
    }
}
