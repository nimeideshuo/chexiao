package com.immo.libcomm.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @author Administrator
 * @content 2列的表格
 * @date 2018/1/10
 */

public class DiverLine {
    public static void setDiver(RelativeLayout linearLayout, int position, Context context){
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) linearLayout
                .getLayoutParams();
        if (position % 2 == 0) {
            lp.setMargins(dip2px(context, 3), dip2px(context, 6), dip2px
                    (context, 6), dip2px(context, 0));
        } else {
            lp.setMargins(dip2px(context, 6), dip2px(context, 6), dip2px
                    (context, 3), dip2px(context, 0));
        }
    }
    public static void setDiver1(RelativeLayout linearLayout, int position, Context context){
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) linearLayout
                .getLayoutParams();
        if (position % 2 == 0) {
            lp.setMargins(dip2px(context, 2), dip2px(context, 0), dip2px
                    (context, 0), dip2px(context, 4));
        } else {
            lp.setMargins(dip2px(context, 0), dip2px(context, 0), dip2px
                    (context, 2), dip2px(context, 4));
        }
    }
    public static void setDiver(LinearLayout linearLayout, int position, Context context){
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) linearLayout
                .getLayoutParams();
        if (position % 2 == 0) {
            lp.setMargins(dip2px(context, 5), dip2px(context, 0), dip2px
                    (context, 10), dip2px(context, 10));
        } else {
            lp.setMargins(dip2px(context, 10), dip2px(context, 0), dip2px
                    (context, 5), dip2px(context, 10));
        }
    }
    /**
     * dip转换px
     */
    public static int dip2px(Context context, int dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

}
