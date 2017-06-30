package io.palaima.debugdrawer.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author Kale
 * @date 2017/6/28
 */
public class EmptyActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.list_content);
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}
