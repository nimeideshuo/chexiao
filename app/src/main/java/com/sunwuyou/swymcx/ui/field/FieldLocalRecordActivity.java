package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.model.FieldSaleThin;
import com.sunwuyou.swymcx.utils.PDH;

import java.util.List;

/**
 * Created by liupiao on
 * 2018/7/30.
 * content
 */
public class FieldLocalRecordActivity extends BaseHeadActivity implements View.OnTouchListener {
    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //            if (menuPopup == null || !menuPopup.isShowing()) {
            //                startActivity(new Intent(FieldLocalRecordActivity.this, FieldEditActivity.class).putExtra("fieldsaleid", tasks.get(position).getId()));
            //            } else {
            //                menuPopup.dismiss();
            //                WindowManager.LayoutParams v1 = getWindow().getAttributes();
            //                v1.alpha = 1f;
            //                getWindow().setAttributes(v1);
            //            }
        }
    };
    private View root;
    private ListView listView;
    private FieldLocalRecordAdapter adapter;
    private ProgressDialog progressDialog;
    private FieldSaleDAO fieldSaleDAO;
    private List<FieldSaleThin> tasks;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.setData(tasks);
            listView.setAdapter(adapter);
//            refreshUI();
        }
    };

    @Override
    public int getLayoutID() {
        return R.layout.act_field_local_records;
    }

    @Override
    public void initView() {
        this.root = this.findViewById(R.id.root);
        this.root.setOnTouchListener(this);
        listView = this.findViewById(R.id.listView);
        listView.setOnTouchListener(this);
        adapter = new FieldLocalRecordAdapter(this);
        this.listView.setAdapter(this.adapter);
        this.listView.setOnItemClickListener(itemClickListener);
        this.listView.setChoiceMode(3);
        //        this.listView.setMultiChoiceModeListener(this.muliChoiceModeLisener);
        //        this.refresh();
        progressDialog = new ProgressDialog(this);
        this.progressDialog.setProgressStyle(1);
        this.progressDialog.setMessage("正在上传中...");
        this.progressDialog.setCancelable(false);
        fieldSaleDAO = new FieldSaleDAO();

    }

    @Override
    public void initData() {

    }

    protected void onResume() {
        super.onResume();
        this.refresh();
    }

    public void refresh() {
        PDH.show(this, new PDH.ProgressCallBack() {
            public void action() {
                tasks = new FieldSaleDAO().queryAllFields();
                handler.sendEmptyMessage(0);
            }
        });
    }

    @Override
    public void setActionBarText() {
        setTitle("我的销售");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //        if (menuPopup != null && (FmenuPopup.isShowing())) {
        //            menuPopup.dismiss();
        //            WindowManager.LayoutParams v0 = getWindow().getAttributes();
        //            v0.alpha = 1f;
        //            getWindow().setAttributes(v0);
        //        }
        return false;
    }
}
