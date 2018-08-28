package com.sunwuyou.swymcx.ui.transfer;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.dao.TransferDocDAO;
import com.sunwuyou.swymcx.dao.TransferItemDAO;
import com.sunwuyou.swymcx.in.EmptyDo;
import com.sunwuyou.swymcx.model.Department;
import com.sunwuyou.swymcx.model.FieldSaleForPrint;
import com.sunwuyou.swymcx.model.FieldSaleItemForPrint;
import com.sunwuyou.swymcx.model.GoodsThin;
import com.sunwuyou.swymcx.model.TransferDoc;
import com.sunwuyou.swymcx.model.TransferItem;
import com.sunwuyou.swymcx.model.TransferItemSource;
import com.sunwuyou.swymcx.model.User;
import com.sunwuyou.swymcx.popupmenu.TransferEditMenuPopup;
import com.sunwuyou.swymcx.print.BTPrintHelper;
import com.sunwuyou.swymcx.print.BTdeviceListAct;
import com.sunwuyou.swymcx.print.PrintData;
import com.sunwuyou.swymcx.print.PrintMode;
import com.sunwuyou.swymcx.service.ServiceUser;
import com.sunwuyou.swymcx.ui.SearchHelper;
import com.sunwuyou.swymcx.ui.field.GoodsSelectMoreDialog;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.UpLoadUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.AutoTextView;
import com.sunwuyou.swymcx.view.MessageDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on
 * 2018/7/30.
 * content
 */
public class TransferEditActivity extends BaseHeadActivity implements View.OnTouchListener {

    List<TransferItemSource> listItems;
    private TransferDoc transferDoc;
    private SearchHelper searchHelper;
    private TransferItemDAO transferItemDAO;
    private TransferItemAdapter adapter;
    private AutoTextView atvSearch;
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            GoodsThin goodsThin = searchHelper.getAdapter().getTempGoods().get(position);
            Intent intent = new Intent();
            intent.setClass(TransferEditActivity.this, TransferAddGoodAct.class);
            intent.putExtra("goods", goodsThin);
            intent.putExtra("transferdocid", transferDoc.getId());
            startActivity(intent);
            atvSearch.setText("");
        }
    };
    private GoodsSelectMoreDialog selectMoreDialog;
    @SuppressLint("HandlerLeak") private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.setData(listItems);
        }
    };
    private View.OnClickListener addMoreListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            List<GoodsThin> v1 = searchHelper.getAdapter().getSelect();
            if (v1 != null && v1.size() != 0) {
                if (!"1".equals(new AccountPreference().getValue("goods_select_more"))) {
                    startActivity(new Intent(TransferEditActivity.this, TransferAddMoreGoodsAct.class).putExtra("goods", JSONUtil.object2Json(v1)).putExtra("transferdoc", transferDoc));
                    atvSearch.setText("");
                } else {
                    if (selectMoreDialog == null) {
                        selectMoreDialog = new GoodsSelectMoreDialog(TransferEditActivity.this);
                    }
                    selectMoreDialog.setAction(new EmptyDo() {
                        public void doAction() {
                            loadData();
                            atvSearch.setText("");
                        }
                    });
                    selectMoreDialog.show(v1, transferDoc.getId());
                }
            }

        }
    };
    private TransferEditMenuPopup menuPopup;
    private View root;
    @SuppressLint("HandlerLeak") private Handler handlerUpload = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: {
                    PDH.showFail(msg.obj.toString());
                    break;
                }
                case 1: {
                    PDH.showSuccess("上传成功");
                    finish();
                    break;
                }
            }

        }
    };
    private SwipeMenuListView listView;

    @Override
    public int getLayoutID() {
        return R.layout.act_transfer;
    }

    @Override
    public void initView() {

        long v0 = this.getIntent().getLongExtra("transferdocid", -1);
        if (v0 <= 0) {
            v0 = new TransferDocDAO().addTransferDoc(this.makeTransferDocForm());
        }
        transferDoc = new TransferDocDAO().getTransferDoc(v0);
        root = findViewById(R.id.root);
        root.setOnTouchListener(this);
        initListView();
        atvSearch = this.findViewById(R.id.atvSearch);
        LinearLayout linearSearch = this.findViewById(R.id.lieSearch);
        searchHelper = new SearchHelper(this, linearSearch, this.transferDoc.getId(), false);
        this.atvSearch.setOnItemClickListener(onItemClickListener);
        Button btnAdd = this.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this.addMoreListener);
        transferItemDAO = new TransferItemDAO();
        //单据不是过账状态 显示菜单
        setTitleRight("菜单", null);
//        if(!this.transferDoc.isIsposted() && !this.transferDoc.isIsupload()) {
//            setTitleRight("菜单", null);
//        }else{
//            setTitleRight(null, null);
//        }
        if ((this.transferDoc.isIsposted()) || (this.transferDoc.isIsupload())) {
            this.isModify = false;
            this.findViewById(R.id.lieSearch).setVisibility(View.GONE);
        }

        if (this.isModify) {
            listView.setItemSwipe(true);
        } else {
            listView.setItemSwipe(false);
        }
    }

    protected void onResume() {
        super.onResume();
        this.loadData();
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        if (this.menuPopup == null) {
            menuPopup = new TransferEditMenuPopup(this, this.transferDoc.getId());
        }
        this.menuPopup.showAtLocation(this.root, 80, 0, 0);
        WindowManager.LayoutParams v0 = this.getWindow().getAttributes();
        v0.alpha = 0.8f;
        this.getWindow().setAttributes(v0);
    }

    @Override
    public void initData() {

    }

    private void initListView() {
        listView = this.findViewById(R.id.listView);
        listView.setOnTouchListener(this);
        adapter = new TransferItemAdapter(this);
        listView.setAdapter(this.adapter);
        listView.setMenuCreator(new SwipeMenuCreator() {
            public void create(SwipeMenu arg11) {
                SwipeMenuItem v1 = new SwipeMenuItem(TransferEditActivity.this.getApplicationContext());
                v1.setTitle("复制");
                v1.setTitleSize(14);
                v1.setTitleColor(-16777216);
                v1.setWidth(100);
                v1.setBackground(new ColorDrawable(Color.rgb(48, 177, 245)));
                arg11.addMenuItem(v1);
                SwipeMenuItem v0 = new SwipeMenuItem(TransferEditActivity.this.getApplicationContext());
                v0.setTitle("删除");
                v0.setTitleSize(14);
                v0.setTitleColor(-16777216);
                v0.setBackground(new ColorDrawable(Color.rgb(201, 201, 206)));
                v0.setWidth(100);
                arg11.addMenuItem(v0);
            }
        });
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            public boolean onMenuItemClick(int arg5, SwipeMenu arg6, int arg7) {
                TransferItem v0 = listItems.get(arg5);
                switch (arg7) {
                    case 0: {
                        modifyItem(new TransferItemDAO().saveTransferItem(v0));
                        break;
                    }
                    case 1: {
                        itemDelete(((TransferItemSource) v0));
                        break;
                    }
                }
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg6, View arg7, int position, long arg9) {
                if (isModify) {
                    if (menuPopup != null && (menuPopup.isShowing())) {
                        menuPopup.dismiss();
                        WindowManager.LayoutParams v1 = getWindow().getAttributes();
                        v1.alpha = 1f;
                        getWindow().setAttributes(v1);
                        return;
                    }
                    modifyItem(listItems.get(position).getSerialid());
                }
            }
        });

    }

    private void loadData() {
        PDH.show(this, new PDH.ProgressCallBack() {
            public void action() {
                listItems = transferItemDAO.getTransferItems(transferDoc.getId());
                handler.sendEmptyMessage(0);
            }
        });
    }

    public List<TransferItemSource> getItems() {
        return this.listItems;
    }

    private void modifyItem(long itemid) {
        Intent intent = new Intent();
        intent.setClass(this, TransferAddGoodAct.class);
        intent.putExtra("itemid", itemid);
        intent.putExtra("transferdocid", this.transferDoc.getId());
        this.startActivity(intent);
    }

    private void itemDelete(final TransferItemSource item) {
        new MessageDialog(this).showDialog("提示", "确定删除该商品吗?", null, null, new MessageDialog.CallBack() {
            @Override
            public void btnOk(View view) {
                if (transferItemDAO.delete(item.getSerialid())) {
                    listItems.remove(item);
                    adapter.notifyDataSetChanged();
                } else {
                    PDH.showFail("删除失败");
                }
            }

            @Override
            public void btnCancel(View view) {

            }
        });
    }

    private TransferDoc makeTransferDocForm() {
        Department v0 = SystemState.getDepartment();
        Object v2 = SystemState.getObject("cu_user", User.class);
        TransferDoc v1 = new TransferDoc();
        v1.setShowid(null);
        v1.setDepartmentid(v0.getDid());
        v1.setDepartmentname(v0.getDname());
        v1.setInwarehouseid(SystemState.getWarehouse().getId());
        v1.setInwarehousename(SystemState.getWarehouse().getName());
        v1.setBuilderid(((User) v2).getId());
        v1.setBuildername(((User) v2).getName());
        v1.setBuildtime(Utils.formatDate(Utils.getCurrentTime(true)));
        v1.setIsposted(false);
        v1.setIsupload(false);
        v1.setRemark("");
        return v1;
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

    public void upload(TransferDoc arg8) {
        TransferDocDAO v1 = new TransferDocDAO();
        UpLoadUtils v2 = new UpLoadUtils();
        String v0 = new ServiceUser().usr_CheckAuthority("NewDiaobodan");
        if (!RequestHelper.isSuccess(v0)) {
            if ("forbid".equals(v0)) {
                v0 = "请联系系统管理员获取授权！";
            }
            this.handlerUpload.sendMessage(this.handlerUpload.obtainMessage(0, v0));
        } else {
            if (arg8.isIsupload()) {
                return;
            }

            if (v1.isEmpty(arg8.getId())) {
                this.handlerUpload.sendMessage(this.handlerUpload.obtainMessage(0, "上传失败，调拨单是空单"));
                return;
            }
            if (v1.isExistsNoBatch(arg8.getId())) {
                this.handlerUpload.sendMessage(this.handlerUpload.obtainMessage(0, "上传失败，存在未选批次的商品"));
                return;
            }

            v0 = v2.uploadTransferDoc(arg8);
            if (v0 != null) {
                this.handlerUpload.sendMessage(this.handlerUpload.obtainMessage(0, v0));
                return;
            }
            this.handlerUpload.sendEmptyMessage(1);
            this.finish();
        }
    }

    public void setActionBarText() {
        setTitle("调拨单");
    }

    public void print() {
        BluetoothAdapter v1 = BluetoothAdapter.getDefaultAdapter();
        if (v1 == null) {
            PDH.showMessage("当前设备不支持蓝牙功能");
        } else if (!v1.isEnabled()) {
            startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 0);
        } else {
            BTPrintHelper printHelper = new BTPrintHelper(this);
            PrintMode printMode = PrintMode.getPrintMode();
            if (printMode != null) {
                printMode.setDatainfo(getDatainfo());
                printMode.setDocInfo(getDocInfo());
                printHelper.setMode(printMode);
                printHelper.print(new AccountPreference().getPrinter());
            }
        }
    }
    public FieldSaleForPrint getDocInfo() {
        FieldSaleForPrint print = new FieldSaleForPrint();
        print.setDoctype("调拨单");
        print.setShowid(transferDoc.getShowid());
        print.setCustomername("某某超市");
        print.setDepartmentname("销售部");
        print.setBuildername(transferDoc.getBuildername());
        print.setBuildtime(transferDoc.getBuildtime());
//        v0.setSumamount("合计:" + Utils.getSubtotalMoney(12000));
//        v0.setReceivable("应收:" + Utils.getSubtotalMoney(12000));
//        v0.setReceived("已收:" + Utils.getSubtotalMoney(2000));
//        v0.setNum("数量:" + String.valueOf(11));
//        v0.setPreference("优惠:" + String.valueOf(0));
        return print;
    }

    public List<FieldSaleItemForPrint> getDatainfo() {
        ArrayList<FieldSaleItemForPrint> printList = new ArrayList<>();
        for (TransferItemSource itemSource : getItems()) {
            FieldSaleItemForPrint item = new FieldSaleItemForPrint();
            item.setGoodsid(itemSource.getGoodsid());
            item.setGoodsname(itemSource.getGoodsname());
            item.setBarcode(itemSource.getBarcode());
            item.setSpecification(itemSource.getSpecification());
            item.setUnitname(itemSource.getUnitname());
            item.setNum(itemSource.getNum());
            item.setRemark(itemSource.getRemark());
            printList.add(item);
        }
        return printList;
    }




    class TransferItemAdapter extends BaseAdapter {
        private Context context;
        private List<TransferItemSource> items;

        TransferItemAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return this.items == null ? 0 : this.items.size();
        }

        public List<TransferItemSource> getData() {
            return this.items;
        }

        public void setData(List<TransferItemSource> items) {
            this.items = items;
            this.notifyDataSetChanged();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return this.items.get(position).getSerialid();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TransferGoodsItem v0;
            if (convertView == null) {
                convertView = LayoutInflater.from(this.context).inflate(R.layout.item_transfer_goods, null);
                v0 = new TransferGoodsItem(convertView);
                convertView.setTag(v0);
            } else {
                v0 = (TransferGoodsItem) convertView.getTag();
            }
            v0.setValue(this.items.get(position));
            v0.tvSerialid.setText(String.valueOf(position + 1));
            return convertView;
        }

        class TransferGoodsItem {
            TextView tvBarcode;
            TextView tvBatch;
            TextView tvName;
            TextView tvNum;
            TextView tvSerialid;
            TextView tvWarehouse;

            TransferGoodsItem(View view) {
                super();
                this.tvSerialid = view.findViewById(R.id.tvSerialid);
                this.tvName = view.findViewById(R.id.tvGoodsName);
                this.tvBarcode = view.findViewById(R.id.tvBarcode);
                this.tvWarehouse = view.findViewById(R.id.tvWarehouse);
                this.tvBatch = view.findViewById(R.id.tvBatch);
                this.tvNum = view.findViewById(R.id.tvNum);
            }

            @SuppressLint("SetTextI18n")
            public void setValue(TransferItemSource arg5) {
                this.tvName.setText(arg5.getGoodsname());
                if (!TextUtils.isEmptyS(arg5.getBarcode())) {
                    this.tvBarcode.setText(arg5.getBarcode());
                }

                this.tvWarehouse.setText(arg5.getWarehousename());
                if (!arg5.getIsusebatch()) {
                    this.tvBatch.setText("");
                } else if (!TextUtils.isEmpty(arg5.getBatch())) {
                    this.tvBatch.setText(arg5.getBatch());
                } else {
                    this.tvBatch.setText("未选批次");
                }

                this.tvNum.setText(String.valueOf(Utils.getNumber(arg5.getNum())) + arg5.getUnitname());
            }
        }
    }
}
