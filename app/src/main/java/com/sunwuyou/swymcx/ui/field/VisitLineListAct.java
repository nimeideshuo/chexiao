package com.sunwuyou.swymcx.ui.field;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toolbar;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.http.BaseUrl;
import com.sunwuyou.swymcx.http.HttpConnect;
import com.sunwuyou.swymcx.http.HttpListener;
import com.sunwuyou.swymcx.response.IDNameEntity;
import com.sunwuyou.swymcx.ui.RegionListAct;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin
 * 2018/7/20.
 * content
 */

public class VisitLineListAct extends BaseHeadActivity {

    @BindView(R.id.base_list)
    RecyclerView baseList;
    private List<IDNameEntity> entityList;
    private Madapter madapter;

    @Override
    public int getLayoutID() {
        return R.layout.activity_base_recyclerview;
    }

    @Override
    public void initView() {
        baseList.setLayoutManager(new LinearLayoutManager(this));
        madapter = new Madapter();
        madapter.bindToRecyclerView(baseList);
    }

    @Override
    public void initData() {
        getNet();
    }

    private void getNet() {
        new HttpConnect(new HttpListener() {
            @Override
            public void loadHttp(Object object, String response) {
                entityList = JSON.parseArray(response, IDNameEntity.class);
                madapter.setNewData(entityList);
            }
        }).jsonPost(BaseUrl.getUrl(BaseUrl.SUPPORT_QUERYVISITLINE), this, null, null, null, true, 0);
    }

    @Override
    public void setActionBar(@Nullable Toolbar toolbar) {
        super.setActionBar(toolbar);
        setTitle("巡店路线");
    }

    private class Madapter extends BaseQuickAdapter<IDNameEntity, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {


        Madapter() {
            super(R.layout.item_base_text, entityList);
            setOnItemClickListener(this);
        }

        @Override
        protected void convert(BaseViewHolder helper, IDNameEntity item) {
            helper.setText(R.id.item_base_text, item.getName());
        }

        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            Intent intent = new Intent();
            intent.putExtra("visitlineid", entityList.get(position).getId());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
