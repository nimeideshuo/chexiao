package com.sunwuyou.swymcx.ui.transfer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dou361.dialogui.DialogUIUtils;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.dao.TransferDocDAO;
import com.sunwuyou.swymcx.model.TransferDoc;
import com.sunwuyou.swymcx.service.ServiceUser;
import com.sunwuyou.swymcx.utils.InfoDialog;
import com.sunwuyou.swymcx.utils.NetUtils;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.UpLoadUtils;
import com.sunwuyou.swymcx.view.MessageDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on
 * 2018/7/30.
 * content 我的调拨
 */
public class TransferLocalRecordActivity extends BaseHeadActivity {
    protected ListView listView;
    protected LinearLayout loadLayout;
    private TransferLocalRecordAdapter adapter;
    private ProgressDialog progressDialog;
    private List<TransferDoc> items;
    public AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
            Intent intent = new Intent(TransferLocalRecordActivity.this, TransferEditActivity.class);
            intent.putExtra("transferdocid", items.get(paramAnonymousInt).getId());
            startActivity(intent);
        }
    };
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.setData(items);
        }
    };
    @SuppressLint("HandlerLeak")
    private Handler handlerProgress = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == -1) {
                progressDialog.show();
                progressDialog.setMax(100);
                progressDialog.setProgress(0);
                return;
            }
            if (message.what == -2) {
                progressDialog.setMax(Integer.parseInt(message.obj.toString()));
                return;
            }
            if (message.what == -3) {
                progressDialog.cancel();
                return;
            }
            progressDialog.setProgress(message.what);
        }
    };
    @SuppressLint("HandlerLeak")
    private Handler handlerUpload = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handlerProgress.sendEmptyMessage(-3);
            switch (msg.what) {
                case 0:
                    InfoDialog.showError(TransferLocalRecordActivity.this, msg.obj.toString());
                    break;
                case 1:
                    InfoDialog.showMessage(TransferLocalRecordActivity.this, "上传成功");
                    break;
                case 2:
                    refresh();
                    break;
            }

        }
    };
    private AbsListView.MultiChoiceModeListener muliChoiceModeLisener = new AbsListView.MultiChoiceModeListener() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            final ArrayList<TransferDoc> v1 = new ArrayList<>();
            int v0;
            for (v0 = 0; v0 < listView.getCount(); ++v0) {
                if (listView.isItemChecked(v0)) {
                    v1.add(items.get(v0));
                }
            }

            switch (item.getItemId()) {
                case R.id.btnDelete: {
                    deleteSelected(v1);
                    break;
                }
                case R.id.btnUpload: {
                    if (!NetUtils.isConnected(TransferLocalRecordActivity.this)) {
                        PDH.showFail("当前无可用网络");
                        return true;
                    }
                    PDH.show(TransferLocalRecordActivity.this, "正在上传...", new PDH.ProgressCallBack() {

                        @Override
                        public void action() {
                            uploadAll(v1);
                        }
                    });
                    break;
                }
                //                case R.id.btnSelectAll:
                //                    break;
            }


            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        }
    };

    private void deleteSelected(final List<TransferDoc> docList) {
        if (docList.size() == 0) {
            PDH.showMessage("请选择要删除的项");
        } else {
            new MessageDialog(this).showDialog("提示", "确定删除选中单据？", null, null, new MessageDialog.CallBack() {
                @Override
                public void btnOk(View view) {
                    PDH.show(TransferLocalRecordActivity.this, "正在删除...", new PDH.ProgressCallBack() {
                        @Override
                        public void action() {
                            TransferDocDAO docDAO = new TransferDocDAO();
                            for (int i = 0; i < docList.size(); i++) {
                                docDAO.deletetransferDoc(docList.get(i).getId());
                            }
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    refresh();
                                }
                            });

                        }
                    });
                }

                @Override
                public void btnCancel(View view) {

                }
            }).showDialog();
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.act_transfer_local_records;
    }

    @Override
    public void initView() {
        this.listView = findViewById(R.id.listView);
        this.loadLayout = findViewById(R.id.loadLayout);
        adapter = new TransferLocalRecordAdapter(this);
        this.listView.setAdapter(this.adapter);
        this.listView.setOnItemClickListener(this.itemClickListener);
        this.listView.setChoiceMode(3);
        this.listView.setMultiChoiceModeListener(this.muliChoiceModeLisener);
        progressDialog = new ProgressDialog(this);
        this.progressDialog.setProgressStyle(1);
        this.progressDialog.setMessage("正在上传中...");
        this.progressDialog.setCancelable(false);
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
                items = new TransferDocDAO().queryAllTransferDoc();
                handler.sendEmptyMessage(0);
            }
        });
    }

    private void uploadAll(List<TransferDoc> arg14) {
        TransferDoc v1;
        TransferDocDAO v5 = new TransferDocDAO();
        UpLoadUtils v6 = new UpLoadUtils();
        String v4 = new ServiceUser().usr_CheckAuthority("NewDiaobodan");
        if (!RequestHelper.isSuccess(v4)) {
            if ("forbid".equals(v4)) {
                v4 = "请联系系统管理员获取授权！";
            }
            this.handlerUpload.sendMessage(this.handlerUpload.obtainMessage(0, v4));
        } else {
            //TODO 等待测试
            int v0 = 0;
            for (int v2 = arg14.size() - 1; v2 >= 0; --v2) {
                v1 = arg14.get(v2);
                if (!v1.isIsupload()) {
                    ++v0;
                    if (v5.isEmpty(v1.getId())) {
                        this.handlerUpload.sendMessage(this.handlerUpload.obtainMessage(0, "上传失败，调拨单是空单"));
                        return;
                    } else if (v5.isExistsNoBatch(v1.getId())) {
                        this.handlerUpload.sendMessage(this.handlerUpload.obtainMessage(0, "上传失败，存在未选批次的商品"));
                        return;
                    }
                }
            }
            this.handlerProgress.sendMessage(this.handlerProgress.obtainMessage(-2, v0));
            int v3 = 0;
            for (int v2 = arg14.size() - 1; v2 >= 0; --v2) {
                v1 = arg14.get(v2);
                if (!v1.isIsupload()) {
                    ++v3;
                    v4 = v6.uploadTransferDoc(v1);
                    if (v0 == 1) {
                        if (v4 != null) {
                            this.handlerUpload.sendMessage(this.handlerUpload.obtainMessage(0, v4));
                        } else {
                            this.handlerUpload.sendEmptyMessage(1);
                        }
                    }

                    this.handlerProgress.sendEmptyMessage(v3);
                }
            }
            this.handlerUpload.sendEmptyMessage(2);
        }

    }

    @Override
    public void setActionBarText() {
        super.setActionBarText();
        setTitle("我的调拨");
    }
}
