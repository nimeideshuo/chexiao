package com.sunwuyou.swymcx.view;

/**
 * Created by admin
 * 2018/7/14.
 * content
 */

import android.graphics.Point;

/**
 * Created by Jackie on 2015/12/25.
 * 自定义点对象
 */
public class PointView extends Point {
    //用于转化密码的下标
    public int index;

    public PointView(int x, int y) {
        super(x, y);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}