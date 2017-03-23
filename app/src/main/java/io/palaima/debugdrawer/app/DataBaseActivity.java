package io.palaima.debugdrawer.app;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import io.palaima.debugdrawer.app.databinding.DatabaseActivityBinding;

/**
 * @author Kale
 * @date 2017/3/22
 */
public class DataBaseActivity extends BaseActivity {

    private SharedPreferences mFirstSp;

    private SharedPreferences mSecondSp;

    private DataBaseHelper mFirstHelper;

    private DataBaseHelper mSecondHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DatabaseActivityBinding b = DataBindingUtil.setContentView(this, R.layout.database_activity);

        initSp();
        initDB();

        renderSpData(b);
        b.refreshSpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renderSpData(b);
            }
        });

        renderDbData(b);
        b.refreshDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renderDbData(b);
            }
        });
    }

    private void renderDbData(DatabaseActivityBinding b) {
        StringBuilder stringBuilder = readDbData(mFirstHelper.queryDataById("1"));
        b.firstDbTv.setText(stringBuilder.toString());

        StringBuilder sb = readDbData(mSecondHelper.queryDataById("1"));
        b.secondDbTv.setText(sb.toString());
    }

    private void renderSpData(DatabaseActivityBinding b) {
        Map<String, ?> firstSpMap = mFirstSp.getAll();
        b.firstSpTv.setText(firstSpMap.toString());
        b.secondSpTv.setText(mSecondSp.getAll().toString());
    }

    @NonNull
    private StringBuilder readDbData(Cursor cursor) {
        StringBuilder stringBuilder = new StringBuilder();
        if (cursor.moveToNext()) {
            stringBuilder.append(cursor.getString(cursor.getColumnIndex(DataBaseHelper.CATEGORY)))
                    .append(cursor.getInt(cursor.getColumnIndex(DataBaseHelper.LINE)))
                    .append(cursor.getString(cursor.getColumnIndex(DataBaseHelper.EXTRA)));
        }
        return stringBuilder;
    }

    private void initSp() {
        mFirstSp = getApplicationContext().getSharedPreferences("first_sp", Context.MODE_PRIVATE);
        mFirstSp.edit()
                .putInt("key_01", 123)
                .putString("key_02", "kale")
                .putBoolean("key_03", true)
                .apply();

        mSecondSp = getSharedPreferences("second_sp", Context.MODE_PRIVATE);
        mSecondSp.edit().putString("name", "kale").apply();

        getSharedPreferences("third_sp", Context.MODE_PRIVATE);

    }

    private void initDB() {
        mFirstHelper = new DataBaseHelper(this, "First-DB");
        mFirstHelper.insertData("类别", 4351, "{name:kale}");

        mSecondHelper = new DataBaseHelper(this, "Second-DB");
        mSecondHelper.insertData("歌名", 232, "晴天");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirstHelper.closeDB();
        mSecondHelper.closeDB();
    }
}
