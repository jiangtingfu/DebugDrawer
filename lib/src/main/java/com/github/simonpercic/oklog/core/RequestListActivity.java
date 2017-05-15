package com.github.simonpercic.oklog.core;

import java.util.List;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import io.palaima.debugdrawer.R;

/**
 * @author Kale
 * @date 2017/5/12
 */
public class RequestListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dd_request_list_activity);

        final List<RequestModel> data = CustomLogManager.sRequestList;

        final ListView listview = (ListView) findViewById(R.id.listview);
        final LayoutInflater inflater = getLayoutInflater();
        final BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public Object getItem(int position) {
                return data.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.dd_request_info_item, null);
                }
                TextView codeTv = ViewHolder.get(convertView, R.id.code_tv);
                TextView methodTv = ViewHolder.get(convertView, R.id.method_tv);
                TextView urlTv = ViewHolder.get(convertView, R.id.url_tv);
                TextView timeTv = ViewHolder.get(convertView, R.id.time_tv);
                
                RequestModel model = data.get(position);
                int code = model.code;
                codeTv.setText(String.valueOf(code));
                codeTv.setTextColor(code == 200 ? Color.GREEN : Color.RED);
                timeTv.setText(model.time);
                methodTv.setText(model.method);
                urlTv.setText(model.url);
                return convertView;
            }
        };
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = Uri.parse(data.get(position).detailUrl);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(data.get(position).detailUrl);
                Toast.makeText(RequestListActivity.this, "url已复制到剪切板~", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        listview.setSelection(data.size());

        findViewById(R.id.clear_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomLogManager.sRequestList.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private static class ViewHolder {

        // I added a generic return type to reduce the casting noise in client code  
        @SuppressWarnings("unchecked")
        public static <T extends View> T get(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<>();
                view.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }
    }

}
