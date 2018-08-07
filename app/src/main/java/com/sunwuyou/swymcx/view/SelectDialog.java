package com.sunwuyou.swymcx.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunwuyou.swymcx.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * 2018/7/14.
 * content
 */

public class SelectDialog extends Dialog {
    private static final String TAGS = SelectDialog.class.getSimpleName();
    onItemClickListener listener;
    private Context mContext;
    private TextView dialogTitle;
    private RecyclerView list;
    private ArrayList<String> dataList;
    private Madapter madapter;

    public SelectDialog(@NonNull Context context, String title, ArrayList<String> datas) {
        super(context, R.style.MyDialog_NoTitle);
        mContext = context;
        setContentView(R.layout.dialog_account_list);
        dialogTitle = findViewById(R.id.dialog_account_title);
        list = findViewById(R.id.dialog_account_list);
        dialogTitle.setText(title);
        dataList = datas;
        initView();
    }

    private void initView() {
        list.setLayoutManager(new LinearLayoutManager(mContext));
        madapter = new Madapter();
        madapter.bindToRecyclerView(list);
    }

    public SelectDialog setCancelables(boolean isCancelable) {
        setCancelable(isCancelable);
        return this;
    }

    public void showDialog() {
        show();
    }

    public SelectDialog onItemClickListener(onItemClickListener listener) {
        this.listener = listener;
        return this;
    }

    public interface onItemClickListener {
        void onItemClick(BaseQuickAdapter adapter, View view, int position);
    }


    /**
     * 主体的列表
     */
    private class Madapter extends BaseQuickAdapter<String, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {


        Madapter() {
            super(R.layout.item_base_text, dataList);
            setOnItemClickListener(this);
        }

        @Override
        protected void convert(BaseViewHolder helper, String name) {
            helper.setText(R.id.item_base_text, name);
        }

        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            if (listener != null) {
                listener.onItemClick(adapter, view, position);
            }
            dismiss();
        }
    }
}
