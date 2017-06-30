package io.palaima.debugdrawer;

import android.view.View;

/**
 * @author Kale
 * @date 2017/6/28
 */
public class DebugWidget {

    protected String title;

    protected View view;

    protected int id;

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
