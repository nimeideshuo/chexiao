package com.baoyz.swipemenulistview;

import android.widget.BaseAdapter;

/**
 * Created by admin on
 * 2018/8/2.
 * content
 */
public abstract class BaseSwipListAdapter extends BaseAdapter {

    public boolean getSwipEnableByPosition(int position){
        return true;
    }

}