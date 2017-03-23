package com.wanjian.sak.config;

import android.content.Context;

import com.wanjian.sak.layer.InfoLayer;
import com.wanjian.sak.layerview.HorizontalMeasureView;
import com.wanjian.sak.layerview.TreeView;
import com.wanjian.sak.layerview.VerticalMeasureView;

/**
 * @author Kale
 * @date 2017/3/22
 * 
 * https://github.com/android-notes/SwissArmyKnife
 */
public class SakConfigBuild extends Config.Build {

    public SakConfigBuild(Context context) {
        super(context);
        for (int i = 0; i < mDefaultLayerViews.size(); i++) {
            if (mDefaultLayerViews.get(i) instanceof TreeView) {
                // 删除布局树
                mDefaultLayerViews.remove(i);
                i--;
                continue;
            }
            if (mDefaultLayerViews.get(i) instanceof HorizontalMeasureView) {
                // 删除布局树
                mDefaultLayerViews.remove(i);
                i--;
                continue;
            }
            if (mDefaultLayerViews.get(i) instanceof VerticalMeasureView) {
                // 删除布局树
                mDefaultLayerViews.remove(i);
                i--;
            }
        }

        for (int i = 0; i < mDefaultLayers.size(); i++) {
            if (mDefaultLayers.get(i) instanceof InfoLayer) {
                // 删除自定义信息
                mDefaultLayers.remove(i);
                i--;
            }
        }
    }

}
