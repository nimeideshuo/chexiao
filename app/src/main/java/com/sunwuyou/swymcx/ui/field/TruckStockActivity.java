package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.dao.GoodsDAO;
import com.sunwuyou.swymcx.model.FieldSaleForPrint;
import com.sunwuyou.swymcx.model.FieldSaleItemForPrint;
import com.sunwuyou.swymcx.model.GoodsThin;
import com.sunwuyou.swymcx.model.User;
import com.sunwuyou.swymcx.print.BTPrintHelper;
import com.sunwuyou.swymcx.print.BTdeviceListAct;
import com.sunwuyou.swymcx.print.ModeHelper;
import com.sunwuyou.swymcx.print.PrintData;
import com.sunwuyou.swymcx.print.PrintMode;
import com.sunwuyou.swymcx.print.PrintMode3;
import com.sunwuyou.swymcx.ui.MAlertDialog;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.MLog;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.UpdateUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.AutoTextView;
import com.sunwuyou.swymcx.view.MessageDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin
 * 2018/7/16.
 * content
 */

public class TruckStockActivity extends BaseHeadActivity {
    @BindView(R.id.atvSearch) AutoTextView atvSearch;
    @BindView(R.id.top) LinearLayout top;
    @BindView(R.id.listView) RecyclerView listView;
    @BindView(R.id.btnPrint) TextView btnPrint;
    private List<GoodsThin> listGoodsThin;
    private MAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    @SuppressLint("HandlerLeak") private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == -1) {
                refresh();
                return;
            }
            if (message.what == 0) {
                adapter.notifyDataSetChanged();
                return;
            }
            MoveToPosition(linearLayoutManager, listView, message.what);
        }
    };
    @SuppressLint("HandlerLeak") private Handler handlerUpdate = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 0) {
                toast(message.obj.toString());
                loadData();
                return;
            }
            if (message.what == 1) {
                listGoodsThin = null;
                refresh();
                return;
            }
            listGoodsThin = null;
            refresh();
        }
    };
    private AccountPreference ap;
    private boolean cbShow;

    public static void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {


        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }
    }

    private void refresh() {
        adapter.setNewData(listGoodsThin);
    }

    @Override
    public int getLayoutID() {
        return R.layout.act_field_truckstock;
    }

    @Override
    public void initView() {
        setTitle("库存【" + SystemState.getWarehouse().getName() + "】");
        setTitleRight("装车", null);
        setTitleRight1("卸车");

    }

    @Override
    protected void onRightClick1() {
        MessageDialog dialog = new MessageDialog(this);
        dialog.showDialog("清除", "卸车后将清除本地库存，确认继续吗？", null, null, new MessageDialog.CallBack() {
            @Override
            public void btnOk(View view) {
                new GoodsDAO().clearStock();
                toast("清除完毕!");
                listGoodsThin = null;
                adapter.setNewData(null);
            }

            @Override
            public void btnCancel(View view) {

            }
        });

    }

    private void loadData() {
        PDH.show(this, "正在刷新...", new PDH.ProgressCallBack() {
            public void action() {
                listGoodsThin = new GoodsDAO().queryGoodsThin(null);
                handler.sendEmptyMessage(-1);
                MLog.d(listGoodsThin.toString());
            }
        });
    }

    @Override
    protected void onRightClick() {
        String updataTime = new AccountPreference().getValue("basic_data_updatitme", "未同步");
        if ((TextUtils.isEmptyS(updataTime)) || ("未同步".equals(updataTime))) {
            toast("请先同步基本数据");
            return;
        }
        if (new GoodsDAO().getTruckGoodsCount() > 0) {
            toast("不能重复装车");
            return;
        }
        PDH.show(this, new PDH.ProgressCallBack() {
            @Override
            public void action() {
                if (new UpdateUtils().executeGoodsStockUpdate(handlerUpdate, SystemState.getWarehouse().getId())) {
                    handlerUpdate.sendMessage(handlerUpdate.obtainMessage(0, "装车成功"));
                }
            }
        });
    }

    @Override
    public void initData() {
        linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        adapter = new MAdapter();
        adapter.bindToRecyclerView(listView);
        ap = new AccountPreference();
        loadData();
    }

    private void printItem() {
        BluetoothAdapter v1 = BluetoothAdapter.getDefaultAdapter();
        if (v1 == null) {
            PDH.showMessage("当前设备不支持蓝牙功能");
        } else if (!v1.isEnabled()) {
            this.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 0);
        } else {
            BTPrintHelper printHelper = new BTPrintHelper(this);
            PrintMode printMode = new PrintMode3(new ModeHelper().getTextViews());
            printMode.setDatainfo(getItems());
            printMode.setDocInfo(getDocPrintInfo());
            printHelper.setMode(printMode);
            printHelper.print(ap.getPrinter());
        }
    }

    public FieldSaleForPrint getDocPrintInfo() {
        FieldSaleForPrint v10 = new FieldSaleForPrint();
        User user = SystemState.getUser();
        v10.setBuildername(user.getName());
        v10.setBuildtime(Utils.formatDate(new Date().getTime(), "yyyy-MM-dd HH:mm:ss"));
        return v10;
    }

    public List<FieldSaleItemForPrint> getItems() {
        List<FieldSaleItemForPrint> datainfo = new ArrayList<>();
        Map<Integer, Boolean> map = adapter.getCheckCb();
        for (Map.Entry<Integer, Boolean> entry : map.entrySet()) {
            GoodsThin goods = listGoodsThin.get(entry.getKey());
            FieldSaleItemForPrint itemForPrint = new FieldSaleItemForPrint();
            itemForPrint.setBarcode(goods.getBarcode());
            itemForPrint.setNum(goods.getStocknumber());
            itemForPrint.setGoodsname(goods.getName());
            itemForPrint.setBigStockNumber(goods.getBigstocknumber());
            itemForPrint.setBusNumber(goods.getBiginitnumber());
            datainfo.add(itemForPrint);
        }
        return datainfo;
    }

    @OnClick(R.id.btnPrint)
    public void onViewClicked() {
        final MAlertDialog dialog = new MAlertDialog(this);
        dialog.setCancelButton(null);
        dialog.simpleShow("是否打印当前商品？");
        dialog.setConfirmButton(new View.OnClickListener() {
            public void onClick(View arg5) {
                dialog.dismiss();
                cbShow = false;
                btnPrint.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                printItem();
                adapter.getCheckCb().clear();
            }
        });
    }

    /**
     * 主体的列表
     */
    private class MAdapter extends BaseQuickAdapter<GoodsThin, BaseViewHolder> implements BaseQuickAdapter.OnItemLongClickListener, BaseQuickAdapter.OnItemClickListener {


        private final HashMap<Integer, Boolean> map;

        MAdapter() {
            super(R.layout.item_field_truckstock, listGoodsThin);
            setOnItemLongClickListener(this);
            setOnItemClickListener(this);
            map = new HashMap<>();
        }

        public Map<Integer, Boolean> getCheckCb() {
            return map;
        }

        @Override
        protected void convert(final BaseViewHolder helper, final GoodsThin item) {
            helper.setText(R.id.tvGoodsName, item.getName());
            helper.setText(R.id.tvGoodsBarcode, item.getBarcode());
            helper.setText(R.id.tvSpecifical, item.getSpecification());
            helper.setText(R.id.tvInit, TextUtils.isEmpty(item.getBiginitnumber()) ? "装车：无库存" : "装车：" + item.getBiginitnumber());
            helper.setText(R.id.tvStock, TextUtils.isEmpty(item.getBigstocknumber()) ? "库存：无库存" : "库存：" + item.getBigstocknumber());
            CheckBox cb = helper.getView(R.id.cbGoods);
            if (cbShow) {
                cb.setVisibility(View.VISIBLE);
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        map.put(helper.getAdapterPosition(), isChecked);
                    }
                });
            } else {
                cb.setVisibility(View.GONE);
            }
            MLog.d("getAdapterPosition    "+helper.getAdapterPosition());
            MLog.d("getLayoutPosition     "+helper.getLayoutPosition());
        }

        @Override
        public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
            cbShow = true;
            btnPrint.setVisibility(View.VISIBLE);
            notifyDataSetChanged();
            return false;
        }

        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            cbShow = false;
            btnPrint.setVisibility(View.GONE);
            map.clear();
            notifyDataSetChanged();
        }
    }

}
