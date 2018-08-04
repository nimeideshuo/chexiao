package com.sunwuyou.swymcx.ui.transfer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.dao.TransferDocDAO;
import com.sunwuyou.swymcx.dao.TransferItemDAO;
import com.sunwuyou.swymcx.model.Department;
import com.sunwuyou.swymcx.model.TransferDoc;
import com.sunwuyou.swymcx.model.TransferItem;
import com.sunwuyou.swymcx.model.TransferItemSource;
import com.sunwuyou.swymcx.model.User;
import com.sunwuyou.swymcx.ui.SearchHelper;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.AutoTextView;

import java.util.List;

/**
 * Created by admin on
 * 2018/7/30.
 * content
 */
public class TransferEditActivity extends BaseHeadActivity implements View.OnTouchListener {

    private TransferDoc transferDoc;
    private RelativeLayout root;
    private SearchHelper searchHelper;
    private TransferItemDAO transferItemDAO;
    private SwipeMenuListView listView;
    private TransferItemAdapter adapter;
    private AutoTextView atvSearch;
    private Button btnAdd;

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
        this.root.setOnTouchListener(this);
        this.initListView();
        atvSearch = this.findViewById(R.id.atvSearch);
        this.linearSearch = this.findViewById(R.id.linearSearch);
        searchHelper = new SearchHelper(this, this.linearSearch, this.transferDoc.getId(), false);
        this.atvSearch.setOnItemClickListener(this.onItemClickListener);
        btnAdd = this.findViewById(R.id.btnAdd);
        this.btnAdd.setOnClickListener(this.addMoreListener);
        transferItemDAO = new TransferItemDAO();

    }

    @Override
    public void initData() {

    }

    private void initListView() {
        listView = this.findViewById(R.id.listView);
        this.listView.setOnTouchListener(this);
        adapter = new TransferItemAdapter(this);
        this.listView.setAdapter(this.adapter);
        this.listView.setMenuCreator(new SwipeMenuCreator() {
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
        this.listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            public boolean onMenuItemClick(int arg5, SwipeMenu arg6, int arg7) {
                //TODO  等待继续
                Object v0 =listItems.get(arg5);
                switch(arg7) {
                    case 0: {
                       modifyItem(new TransferItemDAO().saveTransferItem(((TransferItem)v0)));
                        break;
                    }
                    case 1: {
                     itemDelete(((TransferItemSource)v0));
                        break;
                    }
                }

                return 0;
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
        return false;
    }


    class TransferItemAdapter extends BaseAdapter {
        private Context context;
        private List<TransferItemSource> items;

        public TransferItemAdapter(Context context) {
            this.context = context;
        }

        public void setData(List<TransferItemSource> items) {
            this.items = items;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return this.items == null ? 0 : this.items.size();
        }

        public List<TransferItemSource> getData() {
            return this.items;
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

        public class TransferGoodsItem {
            public TextView tvBarcode;
            public TextView tvBatch;
            public TextView tvName;
            public TextView tvNum;
            public TextView tvSerialid;
            public TextView tvWarehouse;

            public TransferGoodsItem(View view) {
                super();
                this.tvSerialid = view.findViewById(R.id.tvSerialid);
                this.tvName = view.findViewById(R.id.tvName);
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


    public void setActionBarText() {
        setTitle("调拨单");
    }
}
