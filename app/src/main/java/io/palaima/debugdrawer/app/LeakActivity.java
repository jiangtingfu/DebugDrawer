package io.palaima.debugdrawer.app;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;

/**
 * @author Kale
 * @date 2017/3/22
 * 
 * http://droidyue.com/blog/2016/11/14/be-careful-using-getsystemservice/index.html
 */
public class LeakActivity extends BaseActivity {

    /**
     * leak
     */
    private static PowerManager powerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leak_activity);
        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
    }
}
