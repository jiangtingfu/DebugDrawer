package io.palaima.debugdrawer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
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
public class DebugWidgetStore {

    private List<DebugWidget> widgets;

    public static final int DEFAULT_VIEW_ID = -1;

    public DebugWidgetStore(List<DebugWidget> widgets) {
        this.widgets = widgets;
    }

    public List<DebugWidget> getWidgets() {
        return widgets;
    }

    @Nullable
    public DebugWidget getDebugWidget(int viewId) {
        if (viewId == DEFAULT_VIEW_ID) {
            return null;
        }
        for (DebugWidget widget : widgets) {
            if (widget.id == viewId) {
                return widget;
            }
        }
        return null;
    }

    public DebugWidget getCurrentDebugWidget() {
        return widgets.get(widgets.size());
    }

    public static class Builder {

        private Context context;

        private List<DebugWidget> widgetList;

        Builder(Context context) {
            this.context = context;
            widgetList = new ArrayList<>();
        }

        public Builder addText(String title, int content) {
            return addText(title, String.valueOf(content), DEFAULT_VIEW_ID);
        }

        public Builder addText(String title, boolean content) {
            return addText(title, String.valueOf(content), DEFAULT_VIEW_ID);
        }

        public Builder addText(String title, int content, int viewId) {
            return addText(title, String.valueOf(content), viewId);
        }

        public Builder addText(String title, boolean content, int viewId) {
            return addText(title, String.valueOf(content), viewId);
        }

        public Builder addText(String title, String content) {
            return addText(title, content, DEFAULT_VIEW_ID);
        }

        public Builder addText(String title, String content, int viewId) {
            TextView textView = new TextView(new ContextThemeWrapper(context, R.style.Widget_DebugDrawer_Base_RowValue));
            textView.setText(String.format(" %s", content));
            widgetList.add(new DebugWidget(title, textView, viewId));
            return this;
        }

        public Builder addButton(String text, View.OnClickListener listener) {
            return addButton(text, listener, DEFAULT_VIEW_ID);
        }

        public Builder addButton(String text, View.OnClickListener listener, int viewId) {
            Button button = new Button(new ContextThemeWrapper(context, R.style.Widget_DebugDrawer_Base_RowWidget_Black));
            button.setText(text);
            button.setOnClickListener(listener);
            widgetList.add(new DebugWidget(null, button, viewId));
            return this;
        }

        public Builder addIconButton(String text, @DrawableRes int drawableId,
                View.OnClickListener listener) {
            return addIconButton(text, drawableId, listener, DEFAULT_VIEW_ID);
        }

        public Builder addIconButton(String text, @DrawableRes int drawableId,
                View.OnClickListener listener, int id) {

            View view = LayoutInflater.from(context).inflate(R.layout.dd_icon_button, null);
            ((ImageView) view.findViewById(R.id.icon_iv)).setImageResource(drawableId);
            Button button = (Button) view.findViewById(R.id.summary_btn);
            button.setText(text);
            button.setOnClickListener(listener);
            widgetList.add(new DebugWidget(null, view, id));
            return this;
        }

        public Builder addSwitch(String title, boolean checked,
                CompoundButton.OnCheckedChangeListener listener) {
            return addSwitch(title, checked, listener, DEFAULT_VIEW_ID);
        }

        public Builder addSwitch(String title, boolean checked,
                CompoundButton.OnCheckedChangeListener listener, int id) {
            Switch sw = new Switch(new ContextThemeWrapper(context, R.style.Widget_DebugDrawer_Base_RowWidget));
            sw.setChecked(checked);
            sw.setOnCheckedChangeListener(listener);
            widgetList.add(new DebugWidget(title, sw, id));
            return this;
        }

        public DebugWidgetStore build() {
            return new DebugWidgetStore(widgetList);
        }

    }

}
