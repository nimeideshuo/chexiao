package com.sunwuyou.swymcx.ui;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseActivity;
import com.sunwuyou.swymcx.dao.GoodsDAO;
import com.sunwuyou.swymcx.model.GoodsThin;
import com.sunwuyou.swymcx.utils.MLog;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.view.AutoTextView;

import java.util.ArrayList;
import java.util.List;

public class SearchHelper implements View.OnClickListener, AutoTextView.OnTextChangeListener {
    private static List<GoodsThin> listGoods;
    private final int DOUBLE_CLICK = 2;
    private final int SIMPLE_CLICK = 1;
    private int LOADING = 1;
    private int SEARCH = 0;
    private SearchGoodsAdpater adapter;
    private AutoTextView autoTextView;
    private Button btnAdd;
    private int delaytimes;
    private Drawable[] drawables;
    private long fieldsaleid;
    private BaseActivity mBaseGoodsAct;
    private View.OnClickListener onClickListener;
    private AdapterView.OnItemClickListener onItemClickListener;
    private int operation;


    @SuppressLint("HandlerLeak") private Handler clickHandler = new Handler() {
        public void handleMessage(Message paramAnonymousMessage) {
            int v0 = SearchHelper.this.operation;
            SearchHelper.this.operation = 0;
            switch (v0) {
                case 1: {
                    if (autoTextView.isPopupShowing()) {
                        return;
                    }
                    autoTextView.showDropDown();
                    break;
                }
                case 2: {
                    resetSearch();
                    break;
                }
            }
        }


    };
    @SuppressLint("HandlerLeak") private Handler searchGoodsHandler = new Handler() {
        public void handleMessage(Message paramAnonymousMessage) {
            setItemAnimShow(SEARCH);
            if (SearchHelper.listGoods.size() == 0) {
                PDH.showFail("未查到任何数据");
                return;
            }
            adapter.setTempGoods(listGoods);
            adapter.setGoods(listGoods);
            adapter.setIsLoaded(true);
            autoTextView.showDropDown();
        }
    };

    public SearchHelper(BaseActivity paramBaseActivity, View paramView, long fieldsaleid, boolean paramBoolean) {
        this.mBaseGoodsAct = paramBaseActivity;
        this.fieldsaleid = fieldsaleid;
        this.autoTextView = paramView.findViewById(R.id.atvSearch);
        this.btnAdd = paramView.findViewById(R.id.btnAdd);
        listGoods = new ArrayList<>();
        this.autoTextView.setOnClickListener(this);
        this.autoTextView.setOnItemClickListener(this.onItemClickListener);
        this.autoTextView.setOnTextChangeListener(this);
        this.autoTextView.setReplace(false);
        this.adapter = new SearchGoodsAdpater(this.mBaseGoodsAct, paramBoolean);
        this.autoTextView.setAdapter(this.adapter);
        this.btnAdd.setOnClickListener(this.onClickListener);
        this.drawables = new Drawable[2];
        this.drawables[0] = paramBaseActivity.getResources().getDrawable(R.drawable.search);
        this.drawables[1] = paramBaseActivity.getResources().getDrawable(R.drawable.loading);
    }

    public SearchGoodsAdpater getAdapter() {
        return this.adapter;
    }

    public void resetSearch() {
        this.adapter.setGoods(null);
        this.autoTextView.setText("");
    }

    private void searchGoods(final String paramString, final long paramLong) {
        setItemAnimShow(this.LOADING);
        new Thread() {
            public void run() {
                listGoods = new GoodsDAO().queryGoods(paramString.substring(0, 1), paramLong);
                searchGoodsHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    private void setItemAnimShow(int arg5) {
        this.autoTextView.setCompoundDrawablesWithIntrinsicBounds(this.drawables[arg5], null, null, null);
        if (arg5 == this.LOADING) {
            AnimationDrawable stopAnim = (AnimationDrawable) autoTextView.getCompoundDrawables()[0];
            stopAnim.stop();
            AnimationDrawable startAnim = (AnimationDrawable) autoTextView.getCompoundDrawables()[0];
            startAnim.stop();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onChanged(View arg5, String arg6) {
        List v1 = null;
        if (this.mBaseGoodsAct.isModify) {
            if ((TextUtils.isEmpty(arg6)) || (TextUtils.isEmptyS(this.autoTextView.getBeforeTextChange())) || !arg6.startsWith(this.autoTextView.getBeforeTextChange())) {
                this.adapter.setIsUseFull(true);
            } else {
                this.adapter.setIsUseFull(false);
            }

            if (TextUtils.isEmpty(arg6)) {
                MLog.d("clear");
                SearchHelper.listGoods.clear();
                this.adapter.setGoods(v1);
                this.adapter.setTempGoods(v1);
                return;
            }

            if (!TextUtils.isEmptyS(this.autoTextView.getBeforeTextChange()) && (arg6.substring(0, 1).equals(this.autoTextView.getBeforeTextChange().substring(0, 1)))) {
                this.autoTextView.showDropDown();
                return;
            }

            this.adapter.setIsLoaded(false);
            MLog.d("new search");
            this.searchGoods(arg6, this.fieldsaleid);
        }
    }
}