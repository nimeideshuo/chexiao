package com.sunwuyou.swymcx.popupmenu;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.dao.TransferDocDAO;
import com.sunwuyou.swymcx.model.TransferDoc;
import com.sunwuyou.swymcx.model.TransferItemSource;
import com.sunwuyou.swymcx.ui.transfer.TransferEditActivity;
import com.sunwuyou.swymcx.utils.NetUtils;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.view.MessageDialog;

import java.util.List;

/**
 * Created by admin
 * 2018/8/5.
 * content
 */

public class TransferEditMenuPopup extends PopupWindow implements View.OnClickListener {
    private TransferEditActivity activity;
    private Button btnDelete;
    private Button btnUpload;
    private View root;
    private TransferDoc transferDoc;
    private TransferDocDAO transferDocDAO;

    public TransferEditMenuPopup(TransferEditActivity activity, long arg8) {
        super();
        this.activity = activity;
        this.root = LayoutInflater.from(activity).inflate(R.layout.popup_menu_transferedit, null);
        this.setContentView(this.root);
        this.init();
        this.transferDocDAO = new TransferDocDAO();
        this.transferDoc = this.transferDocDAO.getTransferDoc(arg8);
        this.setAnimationStyle(R.style.buttom_in_out);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int v2 = displayMetrics.widthPixels;
        int v0 = displayMetrics.heightPixels;
        this.setWidth(v2);
        this.setHeight(v0 / 11);
        this.setBackgroundDrawable(null);
    }

    private void init() {
        this.btnUpload = this.root.findViewById(R.id.btnUpload);
        this.btnDelete = this.root.findViewById(R.id.btnDelete);
        this.btnUpload.setOnClickListener(this);
        this.btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
        WindowManager.LayoutParams v1 = this.activity.getWindow().getAttributes();
        v1.alpha = 1f;
        this.activity.getWindow().setAttributes(v1);
        switch (v.getId()) {
            case R.id.btnUpload:
                if (!NetUtils.isConnected(this.activity)) {
                    PDH.showFail("当前无可用网络");
                    return;
                }
                List<TransferItemSource> v0 = this.activity.getItems();
                if (v0 != null && v0.size() != 0) {
                    PDH.show(this.activity, "正在上传...", new PDH.ProgressCallBack() {
                        public void action() {
                            activity.upload(transferDoc);
                        }
                    });
                } else {
                    PDH.showFail("不能上传空单");
                }
                break;
            case R.id.btnDelete:
                this.delete();
                break;
        }
    }

    private void delete() {
        new MessageDialog(activity).showDialog("提示", "是否删除单据?", null, null, new MessageDialog.CallBack() {
            @Override
            public void btnOk(View view) {
                transferDocDAO.deletetransferDoc(transferDoc.getId());
                PDH.showSuccess("删除完成");
                activity.finish();
            }

            @Override
            public void btnCancel(View view) {

            }
        }).showDialog();

    }
}
