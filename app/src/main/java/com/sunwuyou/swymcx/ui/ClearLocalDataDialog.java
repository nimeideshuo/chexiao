package com.sunwuyou.swymcx.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.dao.BaseDao;
import com.sunwuyou.swymcx.in.EmptyDo;
import com.sunwuyou.swymcx.utils.PDH;

/**
 * Created by admin on
 * 2018/8/6.
 * content
 */
public class ClearLocalDataDialog extends TitleDialog implements View.OnClickListener {

    private final CheckBox cbClearDatabase;
    private EmptyDo emptyDo;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PDH.showSuccess("删除成功");
            emptyDo.doAction();
        }
    };

    public ClearLocalDataDialog(Activity activity) {
        super(activity);
        setView(R.layout.dia_clear_local_data);
        setTitleText("清空数据");
        cbClearDatabase = this.findViewById(R.id.cbClearDatabase);
        this.setConfirmButton(this);
        this.setCancelButton(this);
    }
    public boolean isChecked() {
        return this.cbClearDatabase.isChecked();
    }
    @Override
    public void onClick(View v) {
        this.dismiss();
        switch (v.getId()) {
            case R.id.btnConfirm:
                PDH.show(this.getActivity(), "正在删除...", new PDH.ProgressCallBack() {
                    public void action() {
                        if (cbClearDatabase.isChecked()) {
                            new BaseDao().deleteLocalData();
                            new BaseDao().deleteDataBase();
                        } else {
                            new BaseDao().deleteLocalData();
                        }
                        handler.sendEmptyMessage(0);
                    }
                });
                break;
        }

    }

    public void setEmptyDo(EmptyDo emptyDo) {
        this.emptyDo = emptyDo;
    }
}
