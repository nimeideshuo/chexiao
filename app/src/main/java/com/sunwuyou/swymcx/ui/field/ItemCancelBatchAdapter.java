package com.sunwuyou.swymcx.ui.field;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.dao.GoodsPriceDAO;
import com.sunwuyou.swymcx.dao.GoodsUnitDAO;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.model.FieldSaleItemBatchEx;
import com.sunwuyou.swymcx.model.GoodsUnit;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.EditButtonView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static java.lang.Integer.valueOf;

/**
 * Created by admin
 * 2018/7/29.
 * content
 */

public class ItemCancelBatchAdapter extends BaseAdapter {
    private Calendar cal;
    private Context context;
    private FieldSale fieldSale;
    private String[] goodsNames;
    private List<GoodsUnit> goodsUnits;
    private List<FieldSaleItemBatchEx> itemBatchs = new ArrayList<>();
    private View.OnLongClickListener onLongClickListener;

    public ItemCancelBatchAdapter(Context context, String goodsid) {
        this.context = context;
        this.goodsUnits = new GoodsUnitDAO().queryGoodsUnits(goodsid);
        this.goodsNames = new String[this.goodsUnits.size()];
        for (int i = 0; i < goodsUnits.size(); i++) {
            this.goodsNames[i] = this.goodsUnits.get(i).getUnitname();
        }
        this.cal = Calendar.getInstance();
    }

    @Override
    public int getCount() {
        return this.itemBatchs == null ? 0 : this.itemBatchs.size();
    }

    public List<FieldSaleItemBatchEx> getData() {
        return this.itemBatchs;
    }

    public void setData(List<FieldSaleItemBatchEx> arg5) {
        this.itemBatchs.clear();
        this.itemBatchs.addAll(arg5);
        if (arg5.size() > 0) {
            this.fieldSale = new FieldSaleDAO().getFieldsale(arg5.get(0).getFieldsaleid());
        }
        this.notifyDataSetChanged();
    }

    public void addItem(FieldSaleItemBatchEx arg4) {
        if (this.itemBatchs == null) {
            this.itemBatchs = new ArrayList<>();
        }
        this.fieldSale = new FieldSaleDAO().getFieldsale(arg4.getFieldsaleid());
        this.itemBatchs.add(arg4);
        this.notifyDataSetChanged();
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
        Item item;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_cancel_batch, null);
            item = new Item(convertView);
            convertView.setTag(item);
        } else {
            item = (Item) convertView.getTag();
        }
        item.setValue(this.itemBatchs.get(position));
        item.etNum.addTextChangedListener(new NumWatcher(position));
        item.etPrice.addTextChangedListener(new PriceWatcher(position));
        item.btnUnit.setOnClickListener(new UnitSelectListener(position));
        item.btnDate.setOnClickListener(new DateClickListener(position));
        item.etBatch.addTextChangedListener(new BatchWatcher(position));
        convertView.setTag(R.id.llDate, position);
        convertView.setOnLongClickListener(this.onLongClickListener);
        return convertView;
    }

    public void setOnLongClicklistener(View.OnLongClickListener paramOnLongClickListener) {
        this.onLongClickListener = paramOnLongClickListener;
    }

    private class BatchWatcher implements TextWatcher {
        private int position = -1;

        public BatchWatcher(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (this.position < itemBatchs.size()) {
                itemBatchs.get(position).setBatch(s.toString());
            }
        }
    }

    private class DateClickListener implements View.OnClickListener {
        private int position = 0;
        private Button btn;
        private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DATE, dayOfMonth);
                btn.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(cal.getTime()));
                itemBatchs.get(position).setProductiondate(Utils.formatDate(cal.getTimeInMillis()));
                if (Utils.intGenerateBatch != 0) {
                    itemBatchs.get(position).setBatch(Utils.generateBatch(cal.getTimeInMillis()));
                }
            }
        };

        public DateClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            this.btn = (Button) v;
            try {
                cal.setTime(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(btn.getText().toString()));
                new DatePickerDialog(ItemCancelBatchAdapter.this.context, this.listener, cal.get(Calendar.YEAR), ItemCancelBatchAdapter.this.cal.get(Calendar.MONTH), ItemCancelBatchAdapter.this.cal.get(Calendar.DATE)).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class UnitSelectListener implements View.OnClickListener {
        private int position = 0;

        public UnitSelectListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            final Button btn = (Button) v;
            AlertDialog.Builder v0 = new AlertDialog.Builder(context);
            v0.setTitle("单位选择");
            v0.setItems(goodsNames, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    btn.setText(goodsNames[which]);
                    if (!itemBatchs.get(position).getUnitid().equals(goodsUnits.get(which).getUnitid())) {
                        if (Utils.isUseCurrentPrice) {
                            double v6 = itemBatchs.get(position).getPrice();
                            if (v6 != 0) {
                                itemBatchs.get(position).setPrice(goodsUnits.get(which).getRatio() * v6 / new GoodsUnitDAO().getGoodsUnit(itemBatchs.get(position).getGoodsid(), itemBatchs.get(position).getUnitid()).getRatio());
                            }
                        } else {
                            double v11 = !Utils.isTuiHuanHuoSamePrice ? 0 : new GoodsPriceDAO().queryGoodsPrice(itemBatchs.get(position).getGoodsid(), goodsUnits.get(which).getUnitid(), fieldSale.getCustomerid(), fieldSale.isIsnewcustomer(), fieldSale.getPricesystemid());
                            itemBatchs.get(position).setPrice(v11);
                        }

                    }
                    itemBatchs.get(position).setUnitid(goodsUnits.get(which).getUnitid());
                    itemBatchs.get(position).setRatio(goodsUnits.get(which).getRatio());
                    itemBatchs.get(position).setUnitname(goodsUnits.get(which).getUnitname());
                    notifyDataSetChanged();
                }
            });
            v0.create().show();
        }
    }

    class PriceWatcher implements TextWatcher {
        private int position = -1;

        public PriceWatcher(int position) {
            this.position = position;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (this.position < itemBatchs.size()) {
                itemBatchs.get(this.position).setPrice(Utils.getDouble(s.toString()));
            }
        }
    }

    class NumWatcher implements TextWatcher {
        private int position = -1;

        public NumWatcher(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (this.position < ItemCancelBatchAdapter.this.itemBatchs.size()) {
                itemBatchs.get(this.position).setNum(Utils.getDouble(s.toString()));
            }
        }
    }

    class Item {
        private Button btnDate;
        private Button btnUnit;
        private EditText etBatch;
        private EditText etNum;
        private EditText etPrice;
        private LinearLayout llBatch;
        private LinearLayout llDate;

        public Item(View arg3) {
            super();
            this.llBatch = arg3.findViewById(R.id.llBatch);
            this.llDate = arg3.findViewById(R.id.llDate);
            this.etBatch = arg3.findViewById(R.id.etBatch);
            this.btnDate = arg3.findViewById(R.id.btnDate);
            this.btnUnit = arg3.findViewById(R.id.btnUnit);
            this.etNum = arg3.findViewById(R.id.etNum);
            this.etPrice = arg3.findViewById(R.id.etPrice);
        }

        public void setValue(FieldSaleItemBatchEx batchEx) {
            if (batchEx.getIsusebatch()) {
                this.llBatch.setVisibility(View.VISIBLE);
                this.llDate.setVisibility(View.VISIBLE);
                this.etBatch.setText(batchEx.getBatch());
                this.btnDate.setText(Utils.formatDate(batchEx.getProductiondate(), "yyyy-MM-dd"));
            } else {
                this.llBatch.setVisibility(View.GONE);
                this.llDate.setVisibility(View.GONE);
            }

            this.btnUnit.setText(batchEx.getUnitname());
            etNum.setText(batchEx.getNum() == 0 ? "" : String.valueOf(batchEx.getNum()));
            etPrice.setText(batchEx.getPrice() == 0 ? "" : String.valueOf(batchEx.getPrice()));
        }

    }

}
