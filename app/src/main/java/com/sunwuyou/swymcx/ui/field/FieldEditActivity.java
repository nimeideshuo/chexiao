package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.dao.FieldSaleItemBatchDAO;
import com.sunwuyou.swymcx.dao.FieldSaleItemDAO;
import com.sunwuyou.swymcx.dao.FieldSalePayTypeDAO;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.model.FieldSaleItemBatchEx;
import com.sunwuyou.swymcx.model.FieldSaleItemSource;
import com.sunwuyou.swymcx.model.FieldSaleItemThin;
import com.sunwuyou.swymcx.model.FieldSaleSum;
import com.sunwuyou.swymcx.model.GoodsThin;
import com.sunwuyou.swymcx.popupmenu.FieldEditMenuPopup;
import com.sunwuyou.swymcx.ui.SearchHelper;
import com.sunwuyou.swymcx.utils.ClickUtils;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.AutoTextView;
import com.sunwuyou.swymcx.view.MessageDialog;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin
 * 2018/7/24.
 * content
 */

public class FieldEditActivity extends BaseHeadActivity implements View.OnTouchListener {

    private FieldSale fieldsale;
    private LinearLayout root;
    private boolean isModify = false;
    private FieldEditMenuPopup menuPopup;


    private FieldsaleItemAdapter adapter;
    private AccountPreference ap;
    private AutoTextView atvSearch;
    private Button btnAdd;
    private Button btnDocSaleInfo;
    private FieldSaleItemDAO fieldsaleitemDAO;
    private LinearLayout linearSearch;
    private List<FieldSaleItemThin> listItems;
    private SwipeMenuListView listView;
    private SearchHelper searchHelper;
    //    private GoodsSelectMoreDialog selectMoreDialog;
    private TextView tvPreference;
    private TextView tvReceivablePrice;
    private TextView tvReceivedPrice;
    private TextView tvSumAmount;
    private View.OnClickListener addMoreListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            List<GoodsThin> v0 = searchHelper.getAdapter().getSelect();
            if (v0 != null && v0.size() != 0) {
                if (ap == null) {
                    ap = new AccountPreference();
                }
                if (!"1".equals(ap.getValue("goods_select_more"))) {
                    startActivity(new Intent().setClass(FieldEditActivity.this, FieldAddMoreGoodsAct.class).putExtra("goods", JSONUtil.object2Json(v0)).putExtra("fieldsale", fieldsale));
                    atvSearch.setText("");
                    return;
                }

            }
        }

    };
    @SuppressLint("HandlerLeak") private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.setData(listItems);
            listView.setAdapter(adapter);
            loadDoc();
            refreshUI();
        }
    };
    private View.OnClickListener saleInfoListener = new View.OnClickListener() {
        public void onClick(View paramAnonymousView) {
            if (ClickUtils.isFastDoubleClick()) {
                return;
            }
            showSaleInfo();
        }
    };
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            GoodsThin goodsThin = searchHelper.getAdapter().getTempGoods().get(position);
            Intent intent = new Intent(FieldEditActivity.this, FieldAddGoodAct.class);
            intent.putExtra("goods", goodsThin);
            intent.putExtra("fieldsaleid", fieldsale.getId());
            startActivity(intent);
            atvSearch.setText("");
        }
    };

    @Override
    public int getLayoutID() {
        return R.layout.act_field;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {
        setTitleRight("菜单", null);
        long l = getIntent().getLongExtra("fieldsaleid", -1L);
        fieldsale = new FieldSaleDAO().getFieldsale(Long.valueOf(l));
        init();
    }

    private void init() {
        this.root = this.findViewById(R.id.root);
        this.root.setOnTouchListener(this);
        initListView();
        this.atvSearch = this.findViewById(R.id.atvSearch);
        this.tvSumAmount = this.findViewById(R.id.tvSumAmount);
        this.tvPreference = this.findViewById(R.id.tvPreference);
        this.tvReceivablePrice = this.findViewById(R.id.tvReceivablePrice);
        this.tvReceivedPrice = this.findViewById(R.id.tvReceivedPrice);
        this.linearSearch = this.findViewById(R.id.lieSearch);
        this.searchHelper = new SearchHelper(this, this.linearSearch, this.fieldsale.getId(), true);
        this.atvSearch.setOnItemClickListener(this.onItemClickListener);
        this.btnAdd = this.findViewById(R.id.btnAdd);
        this.btnAdd.setOnClickListener(this.addMoreListener);
        this.btnDocSaleInfo = this.findViewById(R.id.btnDocSaleInfo);
        this.btnDocSaleInfo.setOnClickListener(this.saleInfoListener);
        this.fieldsaleitemDAO = new FieldSaleItemDAO();
    }

    protected void onResume() {
        super.onResume();
        loadDoc();
        loadData();
    }

    private void loadData() {
        PDH.show(this, new PDH.ProgressCallBack() {
            public void action() {
                listItems = fieldsaleitemDAO.queryDocItems(FieldEditActivity.this.fieldsale.getId());
                handler.sendEmptyMessage(0);
            }
        });
    }

    private void initListView() {
        this.listView = this.findViewById(R.id.listView);
        this.listView.setOnTouchListener(this);
        this.adapter = new FieldsaleItemAdapter(this);
        this.listView.setAdapter(adapter);
        this.listView.setMenuCreator(new SwipeMenuCreator() {
            public void create(SwipeMenu arg11) {
                SwipeMenuItem v1 = new SwipeMenuItem(FieldEditActivity.this.getApplicationContext());
                v1.setTitle("详情");
                v1.setTitleSize(14);
                v1.setTitleColor(-16777216);
                v1.setWidth(100);
                v1.setBackground(new ColorDrawable(Color.rgb(48, 177, 245)));
                arg11.addMenuItem(v1);
                SwipeMenuItem v0 = new SwipeMenuItem(FieldEditActivity.this.getApplicationContext());
                v0.setTitle("删除");
                v0.setTitleSize(14);
                v0.setTitleColor(-16777216);
                v0.setBackground(new ColorDrawable(Color.rgb(201, 201, 206)));
                v0.setWidth(100);
                arg11.addMenuItem(v0);
            }
        });
        this.listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            public boolean onMenuItemClick(int arg5, SwipeMenu arg6, int arg7) {
                FieldSaleItemThin v0 = listItems.get(arg5);
                switch (arg7) {
                    case 0: {
                        itemInfoShow(v0.getSerialid());
                        break;
                    }
                    case 1: {
                        itemDelete(v0);
                        break;
                    }
                }
                return false;
            }
        });
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg7, View arg8, int arg9, long arg10) {
                FieldSaleItemThin v1 = listItems.get(arg9);
                if (!isModify) {
                    itemInfoShow(v1.getSerialid());
                } else {
                    if (menuPopup != null && (menuPopup.isShowing())) {
                         menuPopup.dismiss();
                        WindowManager.LayoutParams v2 = getWindow().getAttributes();
                        v2.alpha = 1f;
                        getWindow().setAttributes(v2);
                        return;
                    }
                    Intent intent = new Intent(FieldEditActivity.this, FieldAddGoodAct.class);
                    intent.putExtra("fieldsaleid", fieldsale.getId());
                    intent.putExtra("itemid", v1.getSerialid());
                    startActivity(intent);
                    atvSearch.setText("");
                }
            }
        });

    }

    private void itemInfoShow(long arg13) {
        FieldSaleItemSource v2 = this.fieldsaleitemDAO.getFieldSaleItem(arg13);
        String v4 = "\n";
        if (v2.getSalenum() > 0) {
            v4 = String.valueOf(v4) + "销售：" + v2.getSalenum() + v2.getSaleunitname() + " Χ " + v2.getSaleprice() + "元/" + v2.getSaleunitname() + "\n\n";
        }
        if (v2.getGivenum() > 0) {
            v4 = String.valueOf(v4) + "赠送：" + v2.getGivenum() + v2.getGiveunitname() + "\n\n";
        }

        if (v2.getCancelbasenum() > 0) {
            List<FieldSaleItemBatchEx> v5 = new FieldSaleItemBatchDAO().queryItemBatchs(this.fieldsale.getId(), v2.getGoodsid(), false);
            int v1;
            for (v1 = 0; v1 < v5.size(); ++v1) {
                FieldSaleItemBatchEx v3 = v5.get(v1);
                v4 = v1 == 0 ? String.valueOf(v4) + "退货：" : String.valueOf(v4) + "；";
                v4 = String.valueOf(v4) + v3.getNum() + v3.getUnitname() + " Χ " + v3.getPrice() + "元/" + v3.getUnitname();
            }
            v4 = String.valueOf(v4) + "\n\n";
        }
        if (v2.getGiftnum() > 0) {
            v4 = String.valueOf(String.valueOf(v4) + "\n") + "促销搭赠【" + v2.getGiftgoodsname() + "】：" + v2.getGiftnum() + v2.getGiftunitname() + "\n\n";
        }
        TextView v6 = new TextView(this);
        v6.setTextSize(16f);
        v6.setPadding(50, 0, 0, 0);
        v6.setText(v4);
        AlertDialog.Builder v0 = new AlertDialog.Builder(this);
        v0.setView(v6);
        v0.setTitle(v2.getGoodsname());
        v0.create().show();
    }

    private void itemDelete(final FieldSaleItemThin arg3) {

        new MessageDialog(this).showDialog("提示", "确定删除该商品吗", null, null, new MessageDialog.CallBack() {
            @Override
            public void btnOk(View view) {
                if (fieldsaleitemDAO.delete(arg3.getSerialid())) {
                    refreshUI();
                    listItems.remove(arg3);
                    adapter.notifyDataSetChanged();
                } else {
                    PDH.showFail("删除失败");
                }
            }

            @Override
            public void btnCancel(View view) {

            }
        }).show();
    }

    public List<FieldSaleItemThin> getItems() {
        return this.listItems;
    }

    @SuppressLint("SetTextI18n")
    public void refreshUI() {
        this.fieldsale = new FieldSaleDAO().getFieldsale(fieldsale.getId());
        double v2 = this.fieldsaleitemDAO.getDocSalePrice(fieldsale.getId());
        double v0 = this.fieldsaleitemDAO.getDocCancelPrice(fieldsale.getId());
        this.tvSumAmount.setText("合计：" + Utils.getRecvableMoney(v2 - v0));
        this.tvPreference.setText("优惠：" + Utils.getRecvableMoney(fieldsale.getPreference()));
        this.tvReceivablePrice.setText("应收：" + Utils.getRecvableMoney(v2 - v0 - this.fieldsale.getPreference()));
        this.tvReceivedPrice.setText("已收：" + new FieldSalePayTypeDAO().getPayTypeAmount(this.fieldsale.getId()));
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        if (menuPopup == null) {
            this.menuPopup = new FieldEditMenuPopup(this);
        }
        this.menuPopup.show(this.root, this.fieldsale.getId());
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.alpha = 0.8F;
        getWindow().setAttributes(localLayoutParams);
    }

    public void loadDoc() {
        this.fieldsale = new FieldSaleDAO().getFieldsale(this.fieldsale.getId());
        if (this.fieldsale.getStatus() == 1 || this.fieldsale.getStatus() == 2) {
            this.isModify = false;
            this.findViewById(R.id.lieSearch).setVisibility(View.GONE);
            this.findViewById(R.id.top).setVisibility(View.GONE);
        } else {
            this.findViewById(R.id.lieSearch).setVisibility(View.VISIBLE);
            this.findViewById(R.id.top).setVisibility(View.VISIBLE);
            this.isModify = true;
        }

        if (!this.isModify) {
            this.listView.setItemSwipe(false);
        } else {
            this.listView.setItemSwipe(true);
        }
    }

    public void print() {

    }

    @Override
    public void initData() {
        setActionBarText();
    }

    public void showSaleInfo() {
        double v7 = 0;
        FieldSaleSum v1 = this.fieldsaleitemDAO.getDocSum(this.fieldsale.getId());
        String v2 = "\n";
        if (v1.getSaleamount() > v7) {
            v2 = String.valueOf(v2) + "销售金额：" + Utils.getRecvableMoney(v1.getSaleamount()) + "元\n\n";
        }

        if (v1.getCancelamount() > v7) {
            v2 = String.valueOf(v2) + "退货金额：" + v1.getCancelamount() + "元\n\n";
        }

        if (this.fieldsale.getPreference() > v7) {
            v2 = String.valueOf(v2) + "优惠金额：" + this.fieldsale.getPreference() + "元\n\n";
        }

        v2 = String.valueOf(v2) + "\n";
        if (v1.getSalenum() > v7) {
            v2 = String.valueOf(v2) + "销售数量：" + v1.getSalenum() + "\n\n";
        }

        if (v1.getGivenum() > v7) {
            v2 = String.valueOf(v2) + "赠送数量：" + v1.getGivenum() + "\n\n";
        }

        if (v1.getCancelnum() > v7) {
            v2 = String.valueOf(v2) + "退货数量：" + v1.getCancelnum() + "\n\n";
        }

        if ((v1.getIsPromotion()) && v1.getPromotionnum() > v7) {
            v2 = String.valueOf(v2) + "\n搭赠数量：" + v1.getPromotionnum() + "\n\n";
        }

        TextView v3 = new TextView(this);
        v3.setTextSize(16f);
        v3.setPadding(50, 0, 0, 0);
        AlertDialog.Builder v0 = new AlertDialog.Builder(this);
        v0.setView(v3);
        v0.setTitle("销售汇总");
        v3.setText(v2);
        v0.create().show();

    }


    public void setActionBarText() {
        String str2 = this.fieldsale.getCustomername();
        String str1 = str2;
        if (TextUtils.isEmptyS(str2)) {
            str1 = "客户";
        }
        setTitle("销售【" + str1 + "】");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (this.menuPopup != null && (this.menuPopup.isShowing())) {
            this.menuPopup.dismiss();
            WindowManager.LayoutParams v0 = this.getWindow().getAttributes();
            v0.alpha = 1f;
            this.getWindow().setAttributes(v0);
        }
        return false;
    }
}
