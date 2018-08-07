package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.MyApplication;
import com.sunwuyou.swymcx.dao.GoodsDAO;
import com.sunwuyou.swymcx.dao.PricesystemDAO;
import com.sunwuyou.swymcx.model.GoodsThin;
import com.sunwuyou.swymcx.request.ReqSynUpdateInfo;
import com.sunwuyou.swymcx.service.ServiceSynchronize;
import com.sunwuyou.swymcx.ui.AllGoodsAdapter;
import com.sunwuyou.swymcx.ui.MAlertDialog;
import com.sunwuyou.swymcx.utils.ClickUtils;
import com.sunwuyou.swymcx.utils.InfoDialog;
import com.sunwuyou.swymcx.utils.MLog;
import com.sunwuyou.swymcx.utils.NetUtils;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.SwyUtils;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.UpdateUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.AutoTextView;
import com.sunwuyou.swymcx.view.BounceListView;
import com.sunwuyou.swymcx.view.MyLetterListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by admin on
 * 2018/7/30.
 * content
 */
public class AllGoodsActivity extends BaseHeadActivity implements AdapterView.OnItemClickListener {

    private ProgressDialog progressDialog;
    private AccountPreference ap;
    private BounceListView bounceListView;
    private MyLetterListView myLetterListView;
    private AutoTextView atvSearch;
    private AutoTextView.OnTextChangeListener changeListener;
    private List<GoodsThin> listGoodsThin;
    private AllGoodsActivity activity;
    private Toast toast;
    private TextView toastView;
    private AllGoodsAdapter adapter;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    refresh();
                    break;
                case 0:
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    MLog.d("select:" + msg.what);
                    bounceListView.setSelection(msg.what);
                    break;
            }

        }
    };
    @SuppressLint("HandlerLeak")
    private Handler handlerUpdate = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                PDH.showSuccess("同步成功");
                if (new PricesystemDAO().getCount() == 0) {
                    PDH.showMessage("无可用的车销价格体系");
                }

                AllGoodsActivity.this.loadData();
            } else {
                if (msg.what == 2) {
                    InfoDialog.showError(AllGoodsActivity.this, "同步失败，请重试");
                    return;
                }

                if (msg.what != -1) {
                    return;
                }
                MyApplication.getInstance().exit();
            }
        }
    };
    @SuppressLint("HandlerLeak")
    private Handler handlerProgress = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -1) {
                progressDialog.setProgress(0);
                progressDialog.setMessage("数据同步中");
                progressDialog.setMax(Integer.parseInt(msg.obj.toString()));
                progressDialog.show();
            } else if (msg.what == -2) {
                progressDialog.setProgress(0);
                progressDialog.setMessage("商品图片同步中");
                progressDialog.setMax(Integer.parseInt(msg.obj.toString()));
                progressDialog.show();
            } else if (msg.what == -3) {
                progressDialog.cancel();
            } else {
                progressDialog.setProgress(msg.what);
            }

        }
    };

    @Override
    protected void onRightClick() {
        super.onRightClick();
        if (!ClickUtils.isFastDoubleClick()) {
            if (!NetUtils.isConnected(this)) {
                PDH.showFail("当前无可用网络");
            } else if (new GoodsDAO().getTruckGoodsCount() > 0) {
                PDH.showMessage("同步前请先执行卸车");
            } else {
                loadBasicData();
            }
        }


    }

    private void loadBasicData() {
        if (!NetUtils.isConnected(this)) {
            PDH.showFail("当前无可用网络");
            return;
        }
        final MAlertDialog dialog = new MAlertDialog(this, 300);
        dialog.setContentText("请选择同步方式");
        dialog.setConfirmButton("全部同步", new View.OnClickListener() {
            public void onClick(View arg4) {
                dialog.dismiss();
                PDH.show(AllGoodsActivity.this, "正在检查更新...", new PDH.ProgressCallBack() {
                    public void action() {
                        getMaxRVersion(true);
                    }
                });
            }
        });
        dialog.show();

    }

    private void getMaxRVersion(boolean arg13) {
        int v11 = 2;
        long v2 = 0;
        if (!arg13) {
            try {
                v2 = Long.parseLong(new AccountPreference().getValue("max_rversion", "0"));
            } catch (Exception v0) {
                v0.printStackTrace();
            }
        }
        List<ReqSynUpdateInfo> v1 = new ServiceSynchronize().syn_QueryUpdateInfo(v2);
        if (v1 == null) {
            this.handlerUpdate.sendEmptyMessage(v11);
        } else {
            long v4 = new SwyUtils().getPagesFromUpdateInfo(v1, "rversion");
            if (v4 >= 0) {
                this.ap.setValue("basic_data_updatitme", Utils.formatDate(new Date().getTime()));
                if (v4 != v2) {
                    if (new UpdateUtils().executeUpdate(this.handlerProgress, v1, v2)) {
                        this.ap.setValue("max_rversion", String.valueOf(v4));
                        this.handlerUpdate.sendEmptyMessage(0);
                    } else {
                        this.handlerUpdate.sendEmptyMessage(v11);
                    }

                    this.handlerProgress.sendEmptyMessage(-3);
                } else {
                    this.handlerUpdate.sendEmptyMessage(0);
                }
            }
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.act_field_all_goods;
    }

    @Override
    public void initView() {
        setTitleRight("同步", null);
        bounceListView = this.findViewById(R.id.listView);
        myLetterListView = this.findViewById(R.id.myLetterListView);
        this.myLetterListView.setOnTouchingLetterChangedListener(new LetterListViewListener(this, null));
        this.myLetterListView.setChooseChar("#");
        atvSearch = this.findViewById(R.id.atvSearch);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        this.progressDialog.setCancelable(false);
        ap = new AccountPreference();
        this.loadData();
        this.initOverLay();
        activity = this;
        this.getWindow().setSoftInputMode(3);
        this.bounceListView.setOnItemClickListener(this);
        this.bounceListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView arg1, int arg2, int arg3, int arg4) {
            }

            public void onScrollStateChanged(AbsListView arg6, int arg7) {
                myLetterListView.setChooseChar(String.valueOf(listGoodsThin.get(bounceListView.getFirstVisiblePosition()).getPinyin()).substring(0, 1).toUpperCase(Locale.CHINA));
            }
        });

        changeListener = new AutoTextView.OnTextChangeListener() {

            @Override
            public void onChanged(View v, String text) {
                if (!TextUtils.isEmptyS(text)) {
                    ArrayList<GoodsThin> listThin = new ArrayList<>();
                    String[] goods_check = Utils.GOODS_CHECK_SELECT.split(",");
                    for (int j = 0; j < listGoodsThin.size(); j++) {
                        for (int i = 0; i < goods_check.length; i++) {
                            String v4 = "";
                            if (goods_check[i].equals("pinyin")) {
                                v4 = listGoodsThin.get(j).getPinyin();
                            } else if (goods_check[i].equals("name")) {
                                v4 = listGoodsThin.get(j).getName();
                            } else if (goods_check[i].equals("barcode")) {
                                v4 = listGoodsThin.get(j).getBarcode();
                            } else if (goods_check[i].equals("id")) {
                                v4 = listGoodsThin.get(j).getId();
                            }
                            if (v4 == null) {
                                continue;
                            }
                            if (v4.contains(text) && (v4.toLowerCase()).contains(text)) {
                                listThin.add(listGoodsThin.get(j));
                                break;
                            }
                        }
                    }
                    adapter.setDate(listThin);
                } else {
                    adapter.setDate(listGoodsThin);
                }
            }
        };
        this.atvSearch.setOnTextChangeListener(this.changeListener);
    }

    private void loadData() {
        PDH.show(this, "正在刷新...", new PDH.ProgressCallBack() {
            public void action() {
                listGoodsThin = new GoodsDAO().queryGoodsThin(false);
                handler.sendEmptyMessage(-1);
            }
        });
    }

    private void refresh() {
        MLog.d("refresh:" + listGoodsThin.toString());
        adapter = new AllGoodsAdapter(this, listGoodsThin);
        this.bounceListView.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    private void initOverLay() {
        this.toast = new Toast(this);
        toastView = ((TextView) getLayoutInflater().inflate(R.layout.toast_overlay, null));
        this.toast.setView(toastView);
        this.toast.setGravity(17, 0, 0);
    }

    @Override
    public void setActionBarText() {
        super.setActionBarText();
        setTitle("产品手册");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent().setClass(this, GoodDetailAct.class).putExtra("goodsid", adapter.getDate().get(position).getId()));
    }

    private void showOverLay(String text) {
        this.toastView.setText(text);
        this.toast.show();
    }

    class LetterListViewListener implements MyLetterListView.OnTouchingLetterChangedListener {
        AllGoodsActivity allGoodsActivity;

        LetterListViewListener(AllGoodsActivity allGoodsActivity, Object object) {
            this.allGoodsActivity = allGoodsActivity;
        }

        public void onTouchingLetterChanged(String s) {
            String v1;
            atvSearch.setText("");
            adapter.setDate(listGoodsThin);
            int v0 = 0;
            if (Character.isLetter(s.toCharArray()[0])) {
                v0 = new GoodsDAO().queryGoodsIndexByPinyin(s);
            }
            bounceListView.setSelection(v0);
            if (Character.isLetter(s.toCharArray()[0])) {
                v1 = s.toUpperCase(Locale.CHINA);
            } else {
                v1 = "#";
            }
            allGoodsActivity.showOverLay(v1);
        }
    }
}
