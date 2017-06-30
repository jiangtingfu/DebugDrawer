package io.palaima.debugdrawer.app;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * @author Kale
 * @date 2017/3/22
 *
 * http://droidyue.com/blog/2016/11/14/be-careful-using-getsystemservice/index.html
 */
public class LeakActivity extends AppCompatActivity {

    /**
     * leak
     */
    private static PowerManager powerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

        LeakClz.getInstance(this);

        ((TextView) findViewById(R.id.content_tv)).setText("LeakCanary");
    }

    private static class LeakClz {

        private static Context context;

        private static LeakClz mInstance = null;

        static LeakClz getInstance(Context context) {
            if (mInstance == null) {
                synchronized (LeakClz.class) {
                    if (mInstance == null) {
                        mInstance = new LeakClz(context);
                    }
                }
            }
            return mInstance;
        }

        LeakClz(Context context) {
            this.context = context;
        }

        private Context getContext() {
            return context;
        }
    }

}
