package io.palaima.debugdrawer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

/**
 * @author Kale
 * @date 2016/4/30
 */
public class DebugWidgets {

    private List<DebugWidgetsBuilder.DebugWidget> widgets;

    public DebugWidgets(List<DebugWidgetsBuilder.DebugWidget> widgets) {
        this.widgets = widgets;
    }

    public List<DebugWidgetsBuilder.DebugWidget> getWidgets() {
        return widgets;
    }

    public static class DebugWidgetsBuilder {

        public static final int DEFAULT_VIEW_ID = -1;

        private Context context;

        private List<DebugWidget> widgetList;

        DebugWidgetsBuilder(Context context) {
            this.context = context;
            widgetList = new ArrayList<>();
        }

        public DebugWidgetsBuilder addText(String title, int content) {
            return addText(title, String.valueOf(content), DEFAULT_VIEW_ID);
        }

        public DebugWidgetsBuilder addText(String title, boolean content) {
            return addText(title, String.valueOf(content), DEFAULT_VIEW_ID);
        }

        public DebugWidgetsBuilder addText(String title, int content, int id) {
            return addText(title, String.valueOf(content), id);
        }

        public DebugWidgetsBuilder addText(String title, boolean content, int id) {
            return addText(title, String.valueOf(content), id);
        }

        public DebugWidgetsBuilder addText(String title, String content) {
            return addText(title, content, DEFAULT_VIEW_ID);
        }

        public DebugWidgetsBuilder addText(String title, String content, int id) {
            TextView textView = new TextView(new ContextThemeWrapper(context, R.style.Widget_DebugDrawer_Base_RowValue));
            textView.setText(" " + content);
            widgetList.add(new DebugWidget(title, textView, id));
            return this;
        }

        public DebugWidgetsBuilder addButton(String text, View.OnClickListener listener) {
            return addButton(text, listener, DEFAULT_VIEW_ID);
        }

        public DebugWidgetsBuilder addButton(String text, View.OnClickListener listener, int id) {
            Button button = new Button(new ContextThemeWrapper(context, R.style.Widget_DebugDrawer_Base_RowWidget_Black));
            button.setText(text);
            button.setOnClickListener(listener);
            widgetList.add(new DebugWidget(null, button, id));
            return this;
        }

        public DebugWidgetsBuilder addIconButton(String text, @DrawableRes int drawableId,
                View.OnClickListener listener) {
            return addIconButton(text, drawableId, listener, DEFAULT_VIEW_ID);
        }

        public DebugWidgetsBuilder addIconButton(String text, @DrawableRes int drawableId,
                View.OnClickListener listener, int id) {

            View view = LayoutInflater.from(context).inflate(R.layout.dd_icon_button, null);
            ((ImageView) view.findViewById(R.id.icon_iv)).setImageResource(drawableId);
            Button button = (Button) view.findViewById(R.id.summary_btn);
            button.setText(text);
            button.setOnClickListener(listener);
            widgetList.add(new DebugWidget(null, view, id));
            return this;
        }

        public DebugWidgetsBuilder addSwitch(String title, boolean checked,
                CompoundButton.OnCheckedChangeListener listener) {
            return addSwitch(title, checked, listener, DEFAULT_VIEW_ID);
        }

        public DebugWidgetsBuilder addSwitch(String title, boolean checked,
                CompoundButton.OnCheckedChangeListener listener, int id) {
            Switch sw = new Switch(new ContextThemeWrapper(context, R.style.Widget_DebugDrawer_Base_RowWidget));
            sw.setChecked(checked);
            sw.setOnCheckedChangeListener(listener);
            widgetList.add(new DebugWidget(title, sw, id));
            return this;
        }

        public DebugWidgets build() {
            return new DebugWidgets(widgetList);
        }

        public DebugWidget getCurrentDebugWidget() {
            return widgetList.get(widgetList.size());
        }

        public static class DebugWidget {

            public String title;

            public View view;

            public int id;

            public DebugWidget(String title, View view, int id) {
                this.title = title;
                this.view = view;
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public <V extends View> V getView() {
                return (V) view;
            }

            public int getId() {
                return id;
            }
        }
    }
}
