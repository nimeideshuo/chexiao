package com.sunwuyou.swymcx.ui.settleup;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.dao.SettleUpDAO;
import com.sunwuyou.swymcx.model.SettleUp;
import com.sunwuyou.swymcx.model.SettleupThin;
import com.sunwuyou.swymcx.service.ServiceUser;
import com.sunwuyou.swymcx.utils.InfoDialog;
import com.sunwuyou.swymcx.utils.NetUtils;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.UpLoadUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.MessageDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on
 * 2018/8/2.
 * content
 */
public class SettletupActivity extends BaseHeadActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ProgressDialog progressDialog;
    private SettleupAdapter adapter;
    private List<SettleupThin> items;
    @SuppressLint("HandlerLeak")
    private Handler handlerProgress = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -1) {
                progressDialog.show();
                progressDialog.setMax(100);
                progressDialog.setProgress(0);
            } else if (msg.what == -2) {
                progressDialog.setMax(Integer.parseInt(msg.obj.toString()));
            } else if (msg.what == -3) {
                progressDialog.cancel();
            } else {
                progressDialog.setProgress(msg.what);
            }
        }
    };
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handlerProgress.sendEmptyMessage(-3);
            switch (msg.what) {
                case 0: {
                    InfoDialog.showError(SettletupActivity.this, msg.obj.toString());
                    break;
                }
                case 1: {
                    InfoDialog.showMessage(SettletupActivity.this, "上传成功");
                    break;
                }
                case 2: {
                    refresh();
                    break;
                }
            }
        }
    };
    AbsListView.MultiChoiceModeListener muliChoiceModeLisener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            adapter.setCheckePosition(position);
            mode.setSubtitle("选中" + listView.getCheckedItemCount() + "条");
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            SettletupActivity.this.adapter.setMultiChoice(true);
            mode.setTitle("选择");
            mode.getMenuInflater().inflate(R.menu.menu_visit_record_context, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.btnDelete:
                    ArrayList<SettleupThin> selectItmes = new ArrayList<>();
                    for (int i = 0; i < listView.getCount(); i++) {
                        if (listView.isItemChecked(i)) {
                            selectItmes.add(items.get(i));
                        }
                    }
                    mode.finish();
                    deleteSelected(selectItmes);
                    return true;
                case R.id.btnUpload:
                    if (!NetUtils.isConnected(SettletupActivity.this)) {
                        PDH.showFail("当前无可用网络");
                        return true;
                    }
                    final ArrayList<SettleupThin> upItmes = new ArrayList<>();
                    for (int i = 0; i < listView.getCount(); i++) {
                        if (listView.isItemChecked(i)) {
                            upItmes.add(items.get(i));
                        }
                    }
                    mode.finish();
                    handlerProgress.sendEmptyMessage(-1);
                    new Thread() {
                        public void run() {
                            uploadAll(upItmes);
                        }
                    }.start();
                    return true;
                case R.id.btnSelectAll:
                    for (int i = 0; i < adapter.getCount(); i++) {
                        if (!listView.isItemChecked(i)) {
                            listView.setItemChecked(i, true);
                        }
                    }
                    break;
            }


            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.setMultiChoice(false);
        }
    };

    private void uploadAll(List<SettleupThin> arg19) {
        SettleUpDAO v10 = new SettleUpDAO();
        UpLoadUtils v12 = new UpLoadUtils();
        int v6 = 0;
        int v5 = 0;
        int v4 = 0;
        for (int i = 0; i < arg19.size(); i++) {
            SettleupThin v11 = arg19.get(i);
            if (!v11.isIssubmit()) {
                long v14 = v11.getId();
                boolean v13 = !v11.getType().equals("63");
                if (v10.isEmpty(v14, v13)) {
                    this.handler.sendMessage(this.handler.obtainMessage(0, "上传失败，客户【" + v11.getObjectname() + " 】的结算单是空单"));
                    return;
                }
                if (!v10.isSettleup(v14, v13)) {
                    this.handler.sendMessage(this.handler.obtainMessage(0, "上传失败，客户【" + v11.getObjectname() + " 】的结算单未结清"));
                    return;
                }
                if (v11.getType().equals("63")) {
                    v6 = 1;
                    break;
                }

                if (v11.getType().equals("64")) {
                    v5 = 1;
                    break;
                }
                if (v11.getType().equals("62")) {
                    v4 = 1;
                    break;
                }
            }
        }
        String v1 = "";
        if (v6 != 0) {
            v1 = v1.length() > 0 ? String.valueOf(v1) + ",NewShoukuandan" : String.valueOf(v1) + "NewShoukuandan";
        }

        if (v5 != 0) {
            v1 = v1.length() > 0 ? String.valueOf(v1) + ",NewQitashoukuandan" : String.valueOf(v1) + "NewQitashoukuandan";
        }

        if (v4 != 0) {
            v1 = v1.length() > 0 ? String.valueOf(v1) + ",NewQitafukuandan" : String.valueOf(v1) + "NewQitafukuandan";
        }

        if (v1.length() > 0) {
            String v8 = new ServiceUser().usr_CheckAuthority(v1);
            if (!RequestHelper.isSuccess(v8)) {
                if ("forbid".equals(v8)) {
                    v8 = "请联系系统管理员获取授权！";
                }
                this.handler.sendMessage(this.handler.obtainMessage(0, v8));
                return;
            }
        }
        this.handlerProgress.sendMessage(this.handlerProgress.obtainMessage(-2, arg19.size()));
        for (int i = 0; i < arg19.size(); i++) {
            SettleupThin v11 = arg19.get(i);
            if (!v11.isIssubmit()) {
                SettleUp v9 = v10.getSettleUp(v11.getId());
                String v8 = v11.getType().equals("63") ? v12.uploadSettleUp(v9) : v12.uploadOtherSettleUp(v9);
                if (v8 != null) {
                    this.handler.sendMessage(this.handler.obtainMessage(0, v8));
                    return;
                }
                this.handlerProgress.sendEmptyMessage(i);
            }
        }
        this.handler.sendEmptyMessage(1);
        this.handler.sendEmptyMessage(2);
    }

    protected void onRestart() {
        super.onRestart();
        this.refresh();
    }

    private void deleteSelected(final List<SettleupThin> settleups) {
        if (settleups.size() == 0) {
            PDH.showMessage("请选择要删除的项");
        } else {
            new MessageDialog(this).showDialog("提示", "确定删除选中单据？", null, null, new MessageDialog.CallBack() {
                @Override
                public void btnOk(View view) {
                    PDH.show(SettletupActivity.this, new PDH.ProgressCallBack() {
                        @Override
                        public void action() {
                            for (int i = 0; i < settleups.size(); i++) {
                                new SettleUpDAO().deleteSettleUp(settleups.get(i).getId());
                            }
                            mHandler.post(new Runnable() {
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
        return R.layout.act_settleup;
    }

    @Override
    public void initView() {
        listView = this.findViewById(R.id.listView);
        adapter = new SettleupAdapter(this);
        this.listView.setAdapter(this.adapter);
        this.refresh();
        this.listView.setOnItemClickListener(this);
        this.listView.setChoiceMode(3);
        this.listView.setMultiChoiceModeListener(muliChoiceModeLisener);
        progressDialog = new ProgressDialog(this);
        this.progressDialog.setProgressStyle(1);
        this.progressDialog.setMessage("正在上传中...");
        this.progressDialog.setCancelable(false);
    }

    @Override
    public void initData() {

    }

    public void refresh() {
        PDH.show(this, new PDH.ProgressCallBack() {
            @Override
            public void action() {
                items = new SettleUpDAO().getSettleUps();
                mHandler.post(new Runnable() {
                    public void run() {
                        adapter.setData(items);
                    }
                });
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SettleupThin v1 = items.get(position);
        Intent intent = new Intent();
        intent.putExtra("settleupid", v1.getId());
        if (v1.getType().equals("63")) {
            intent.setClass(SettletupActivity.this, SettleupDocsActivity.class);
        } else {
            intent.setClass(SettletupActivity.this, OtherSettleupDocActivity.class);
        }
        startActivity(intent);
    }

    public void setActionBarText() {
        setTitle("我的结算");
    }

    public class SettleupAdapter extends BaseAdapter {
        private Context context;
        private List<SettleupThin> listItems;
        private boolean multichoice;
        private HashMap<Integer, Boolean> statusMap;

        @SuppressLint("UseSparseArrays")
        public SettleupAdapter(Context context) {
            this.statusMap = new HashMap<>();
            this.multichoice = false;
            this.context = context;
        }

        @Override
        public int getCount() {
            return this.listItems == null ? 0 : this.listItems.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Item v0;
            if (convertView == null) {
                convertView = LayoutInflater.from(this.context).inflate(R.layout.item_settleup, null);
                v0 = new Item(convertView);
                convertView.setTag(v0);
            } else {
                v0 = (Item) convertView.getTag();
            }
            if (statusMap.get(position) == null) {
                v0.cbTV.setChecked(false);
            } else {
                v0.cbTV.setChecked(this.statusMap.get(position));
            }
            v0.tvId.setText(String.valueOf(this.getCount() - position));
            v0.setValue(listItems.get(position));
            return convertView;
        }

        public void setData(List<SettleupThin> listItems) {
            this.listItems = listItems;
            this.notifyDataSetChanged();
        }

        public void setMultiChoice(boolean multichoice) {
            this.multichoice = multichoice;
            this.notifyDataSetChanged();
        }

        public void setCheckePosition(int position) {
            if (this.statusMap.get(position) == null) {
                this.statusMap.put(position, true);
            } else {
                statusMap.put(position, !statusMap.get(position));
            }
            this.notifyDataSetChanged();
        }

        class Item {
            private CheckedTextView cbTV;
            private TextView tvCustomerName;
            private TextView tvId;
            private TextView tvReceivableAmount;
            private TextView tvReceivedAmount;

            public Item(View view) {
                super();
                this.tvId = view.findViewById(R.id.tvId);
                this.tvCustomerName = view.findViewById(R.id.tvCustomerName);
                this.tvReceivableAmount = view.findViewById(R.id.tvReceivableAmount);
                this.tvReceivedAmount = view.findViewById(R.id.tvReceivedAmount);
                this.cbTV = view.findViewById(R.id.cbTV);
            }


            @SuppressLint("SetTextI18n")
            public void setValue(SettleupThin arg6) {
                String v0 = "";
                if (arg6.getType().equals("64")) {
                    v0 = "【其他收入】";
                } else if (arg6.getType().equals("62")) {
                    v0 = "【费用支出】";
                } else if (arg6.getType().equals("63")) {
                    v0 = "【收款】";
                }
                this.tvCustomerName.setText(v0.isEmpty() ? arg6.getObjectname() : v0 + " " + arg6.getObjectname());
                if (arg6.isIssubmit()) {
                    this.tvCustomerName.setTextColor(-65536);
                } else {
                    this.tvCustomerName.setTextColor(-16777216);
                }

                if (arg6.getType().equals("64")) {
                    this.tvReceivableAmount.setText("应收：" + Utils.getRecvableMoney(arg6.getReceivableamount()));
                    this.tvReceivedAmount.setText("已收：" + Utils.getRecvableMoney(arg6.getReceivedamount()));
                } else if (arg6.getType().equals("62")) {
                    this.tvReceivableAmount.setText("应付：" + Utils.getRecvableMoney(arg6.getReceivableamount()));
                    this.tvReceivedAmount.setText("已付：" + Utils.getRecvableMoney(arg6.getReceivedamount()));
                } else if (arg6.getType().equals("63")) {
                    this.tvReceivableAmount.setText("应收：" + Utils.getRecvableMoney(arg6.getReceivableamount()));
                    this.tvReceivedAmount.setText("已收：" + Utils.getRecvableMoney(arg6.getReceivedamount()));
                }

                if (SettleupAdapter.this.multichoice) {
                    this.cbTV.setVisibility(View.VISIBLE);
                } else {
                    statusMap.clear();
                    this.cbTV.setVisibility(View.GONE);
                }
            }
        }
    }
}
