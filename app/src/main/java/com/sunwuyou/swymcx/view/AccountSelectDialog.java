package com.sunwuyou.swymcx.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.http.BaseUrl;
import com.sunwuyou.swymcx.http.HttpConnect;
import com.sunwuyou.swymcx.http.HttpListener;
import com.sunwuyou.swymcx.model.AccountSetEntity;
import com.sunwuyou.swymcx.ui.login.LoginAct;

import java.util.List;

/**
 * Created by admin
 * 2018/7/14.
 * content
 */

public class AccountSelectDialog extends Dialog {
    private static final String TAGS = AccountSelectDialog.class.getSimpleName();
    private Activity mContext;
    private TextView dialogTitle;
    private RecyclerView list;
    private List<AccountSetEntity> entityList;
    private Madapter madapter;

    public AccountSelectDialog(@NonNull Activity context) {
        super(context, R.style.MyDialog_NoTitle);
        mContext = context;
        setContentView(R.layout.dialog_account_list);
        dialogTitle = findViewById(R.id.dialog_account_title);
        list = findViewById(R.id.dialog_account_list);
        initView();
    }

    private void initView() {
        list.setLayoutManager(new LinearLayoutManager(mContext));
        madapter = new Madapter();
        madapter.bindToRecyclerView(list);
    }

    public void showDialog(String title) {
        dialogTitle.setText(title);
        querySaccountNet();
    }

    //查询工作账套
    private void querySaccountNet() {
        new HttpConnect(new HttpListener() {
            @Override
            public void loadHttp(Object object, String response) {
                entityList = JSON.parseArray(response, AccountSetEntity.class);
                madapter.setNewData(entityList);
                show();
            }
        }).jsonPost(BaseUrl.getUrl(BaseUrl.SUPPORT_QUERYSACCOUNTSET), mContext, null, null, null, true, 0);

    }

    private void finish(){
        mContext.finish();
    }


    /**
     * 主体的列表
     */
    private class Madapter extends BaseQuickAdapter<AccountSetEntity, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {


        Madapter() {
            super(R.layout.item_base_text, entityList);
            setOnItemClickListener(this);
        }

        @Override
        protected void convert(BaseViewHolder helper, AccountSetEntity item) {
            helper.setText(R.id.item_base_text, item.getName());
        }

        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            SystemState.setValue("accountset", JSON.toJSONString(entityList.get(position)));
            mContext.startActivity(new Intent(mContext, LoginAct.class));
            finish();
        }
    }
}
