/*
 * Copyright (C) 2015 Mantas Palaima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.palaima.debugdrawer;

import java.util.List;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import io.palaima.debugdrawer.util.UIUtils;
import io.palaima.debugdrawer.view.ScrimInsetsFrameLayout;

public class DebugWidgetsFrame {

    private ViewGroup rootView;

    private Activity activity;

    private DrawerLayout drawerLayout;

    private ScrollView sliderLayout;

    private int drawerGravity = Gravity.END;

    private List<IDebugModule> debugModules;

    private DrawerLayout.DrawerListener onDrawerListener;

    /**
     * Set the gravity for the drawer. START, LEFT | RIGHT, END
     */
    public DebugWidgetsFrame(Activity activity, List<IDebugModule> debugModules) {
        this.activity = activity;
        this.rootView = (ViewGroup) activity.findViewById(android.R.id.content);
        this.debugModules = debugModules;
        if (rootView == null || rootView.getChildCount() == 0) {
            throw new RuntimeException(
                    "You have to set your layout for this activity with setContentView() first.");
        }
        init();
    }

    /**
     * Build and add the Drawer to your activity
     */
    private void init() {
        setDrawerLayout();

        //get the drawer root
        ScrimInsetsFrameLayout drawerContentRoot = (ScrimInsetsFrameLayout) drawerLayout.getChildAt(0);

        //get the content view
        View contentView = getContentView();

        //add the contentView to the drawer content frameLayout
        drawerContentRoot.addView(contentView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        //add the drawerLayout to the root
        rootView.addView(drawerLayout, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        sliderLayout = (ScrollView) drawerLayout.findViewById(R.id.dd_slider_layout);
        setSliderLayout();

        GridLayout moduleGl = ((GridLayout) sliderLayout.findViewById(R.id.dd_module_gl));
        for (IDebugModule module : debugModules) {
            inflateModules(module, moduleGl);
        }
        activity = null;
    }

    private void setSliderLayout() {
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) sliderLayout.getLayoutParams();
        if (params != null) {
            // if we've set a custom gravity set it
            if (drawerGravity != 0) {
                params.gravity = drawerGravity;
            }
            // if this is a drawer from the right, change the margins :D
            params = processDrawerLayoutParams(params);
            // set the new layout params
            sliderLayout.setLayoutParams(params);
        }
    }

    private View getContentView() {
        View contentView = rootView.getChildAt(0);
        boolean alreadyInflated = contentView instanceof DrawerLayout;

        //only add the new layout if it wasn't done before
        if (!alreadyInflated) {
            // remove the contentView
            rootView.removeView(contentView);
        } else {
            //if it was already inflated we have to clean up again
            rootView.removeAllViews();
        }
        return contentView;
    }

    private void setDrawerLayout() {
        drawerLayout = (DrawerLayout) activity.getLayoutInflater()
                .inflate(R.layout.dd_debug_drawer, rootView, false);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                for (IDebugModule module : debugModules) {
                    module.onDrawerOpened();
                }
            }
        });
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (onDrawerListener != null) {
                    onDrawerListener.onDrawerSlide(drawerView, slideOffset);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (onDrawerListener != null) {
                    onDrawerListener.onDrawerOpened(drawerView);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (onDrawerListener != null) {
                    onDrawerListener.onDrawerClosed(drawerView);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void inflateModules(IDebugModule module, GridLayout gl) {
        module.setActivity(activity);
        View titleV = LayoutInflater.from(activity).inflate(R.layout.dd_module_title, null, false);
        ((TextView) titleV.findViewById(R.id.title_tv)).setText(module.getName());
        GridLayout.LayoutParams params = getSpanColParams(gl);
        params.setMargins(0, 20, 0, 3);
        gl.addView(titleV, params);

        DebugWidgets widgets = module.createWidgets(new DebugWidgets.DebugWidgetsBuilder(activity));
        if (widgets == null) {
            gl.removeView(titleV);
            return;
        }

        for (DebugWidgets.DebugWidgetsBuilder.DebugWidget widget : widgets.getWidgets()) {
            if (widget.title != null) {
                // add title widget
                TextView tv = new TextView(new ContextThemeWrapper(activity, R.style.Widget_DebugDrawer_Base_RowTitle));
                tv.setText(widget.title);
                gl.addView(tv);

                // add summary widget
                gl.addView(widget.view);
            } else {
                gl.addView(widget.view, getSpanColParams(gl));
            }
        }
    }

    @NonNull
    private GridLayout.LayoutParams getSpanColParams(GridLayout gl) {
        // http://bbs.csdn.net/topics/391853628
        // 行设置，第一个为参数为第几行，默认可不设置，第二个参数为跨行数，没有则表示不跨行
        GridLayout.Spec row = GridLayout.spec(GridLayout.UNDEFINED);
        // 列设置，第一个为参数为第几列，默认可不设置，第二个参数为跨列数，没有则表示不跨行
        GridLayout.Spec col = GridLayout.spec(GridLayout.UNDEFINED, gl.getColumnCount());
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(row, col);
        params.width = GridLayout.LayoutParams.MATCH_PARENT;
        return params;
    }

    /**
     * helper to extend the layoutParams of the drawer
     */
    private DrawerLayout.LayoutParams processDrawerLayoutParams(DrawerLayout.LayoutParams params) {
        if (params != null) {
            if (drawerGravity != 0 && (drawerGravity == Gravity.RIGHT || drawerGravity == Gravity.END)) {
                params.rightMargin = 0;
                if (Build.VERSION.SDK_INT >= 17) {
                    params.setMarginEnd(0);
                }

                params.leftMargin = activity.getResources().getDimensionPixelSize(R.dimen.dd_debug_drawer_margin);
                if (Build.VERSION.SDK_INT >= 17) {
                    params.setMarginEnd(activity.getResources().getDimensionPixelSize(R.dimen.dd_debug_drawer_margin));
                }
            }
            params.width = UIUtils.getOptimalDrawerWidth(activity);
        }

        return params;
    }

    public void setDrawerListener(DrawerLayout.DrawerListener onDrawerListener) {
        this.onDrawerListener = onDrawerListener;
    }


    public void resume() {
        for (IDebugModule module : debugModules) {
            module.onActivityResume();
        }
    }

    public void destroy() {
        for (IDebugModule module : debugModules) {
            module.onActivityDestroy();
        }
    }

    /**
     * Open the drawer
     */
    public void openDrawer() {
        if (drawerLayout != null && sliderLayout != null) {
            if (drawerGravity != 0) {
                drawerLayout.openDrawer(drawerGravity);
            } else {
                drawerLayout.openDrawer(sliderLayout);
            }
        }
    }

    /**
     * close the drawer
     */
    public void closeDrawer() {
        if (drawerLayout != null) {
            if (drawerGravity != 0) {
                drawerLayout.closeDrawer(drawerGravity);
            } else {
                drawerLayout.closeDrawer(sliderLayout);
            }
        }
    }

    /**
     * Get the current state of the drawer.
     * True if the drawer is currently open.
     */
    public boolean isDrawerOpened() {
        return drawerLayout != null && sliderLayout != null && drawerLayout.isDrawerOpen(sliderLayout);
    }

    /**
     * Enable or disable interaction with all drawers.
     */
    public void setDrawerLockMode(int lockMode) {
        if (drawerLayout != null && sliderLayout != null) {
            drawerLayout.setDrawerLockMode(lockMode);
        }
    }
}
