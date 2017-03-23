package io.palaima.debugdrawer.app;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author Kale
 * @date 2017/3/22
 */
public class UiActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_activity);
    }
}
