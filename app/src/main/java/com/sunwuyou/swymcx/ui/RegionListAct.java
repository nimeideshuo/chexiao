package com.sunwuyou.swymcx.ui;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.http.BaseUrl;
import com.sunwuyou.swymcx.http.HttpConnect;
import com.sunwuyou.swymcx.http.HttpListener;
import com.sunwuyou.swymcx.response.IDNameEntity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by admin
 * 2018/7/20.
 * content
 */

public class RegionListAct extends BaseHeadActivity {
    @BindView(R.id.base_list)
    RecyclerView baseList;
    private Madapter madapter;
    private List<IDNameEntity> entityList;

    @Override
    public int getLayoutID() {
        return R.layout.activity_base_recyclerview;
    }

    @Override
    public void initView() {
        baseList.setLayoutManager(new LinearLayoutManager(this));
        //添加Android自带的分割线
        baseList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
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
        }).jsonPost(BaseUrl.getUrl(BaseUrl.SUPPORT_QUERYREGION), this, null, null, null, true, 0);
    }

    public void setActionBarText() {
        setTitle("地区");
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
            intent.putExtra("regionid", entityList.get(position).getId());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
