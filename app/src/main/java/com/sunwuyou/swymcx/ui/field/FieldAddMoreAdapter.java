package com.sunwuyou.swymcx.ui.field;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.dao.GoodsUnitDAO;
import com.sunwuyou.swymcx.model.FieldSaleItemSource;
import com.sunwuyou.swymcx.model.GoodsUnit;
import com.sunwuyou.swymcx.model.RespGoodsPriceEntity;
import com.sunwuyou.swymcx.utils.DocUtils;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * 2018/7/28.
 * content
 */

public class FieldAddMoreAdapter extends BaseAdapter {
    Context context;
    private List<FieldSaleItemSource> listItems;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Button button = (Button) v;
            int v4 = Integer.parseInt(v.getTag().toString());
            final List<GoodsUnit> v5 = new GoodsUnitDAO().queryGoodsUnits(listItems.get(v4).getGoodsid());
            final String[] v3 = new String[v5.size()];
            int v7;
            for (v7 = 0; v7 < v5.size(); ++v7) {
                v3[v7] = v5.get(v7).getUnitname();
            }

            AlertDialog.Builder v6 = new AlertDialog.Builder(FieldAddMoreAdapter.this.context);
            v6.setTitle("单位选择");
            v6.setItems(v3, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    button.setText(v3[which]);
                    listItems.get(which).setSaleunitid(v5.get(which).getUnitid());
                    listItems.get(which).setSaleunitname(v5.get(which).getUnitname());
                }
            });
            v6.create().show();


        }
    };

    private View.OnClickListener onBarcodeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = (Integer) v.getTag();
//            final TextView price = (TextView) v;
            final FieldSaleItemSource itemXS = listItems.get(i);
            final List<RespGoodsPriceEntity> listGoodPrice = DocUtils.queryGoodsPriceList(itemXS.getGoodsid());
            if (listGoodPrice.isEmpty()) {
                PDH.showFail("没有查询到价格!");
                return;
            }
            String arrayPrice[] = new String[listGoodPrice.size()];
            for (int j = 0; j < listGoodPrice.size(); j++) {
                RespGoodsPriceEntity priceEntity = listGoodPrice.get(j);
                arrayPrice[j] = priceEntity.getPricesystemname() + ":" + priceEntity.getPrice();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("商品价格");
            builder.setItems(arrayPrice, new AlertDialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
//                    double price2 = listGoodPrice.get(which).getPrice();
//                    price.setText("单价:" + price2 + "元");
                }
            });
            builder.show();
        }
    };


        public FieldAddMoreAdapter(Context context) {
        super();
        this.context = context;
        this.listItems = new ArrayList<>();
    }

    public List<FieldSaleItemSource> getData() {
        return this.listItems;
    }

    public void setData(List<FieldSaleItemSource> listItems) {
        this.listItems.clear();
        this.listItems.addAll(listItems);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.listItems.size();
    }

    @Override
    public FieldSaleItemSource getItem(int position) {
        return this.listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_add_more_goods, null);
            item = new Item(convertView);
            convertView.setTag(item);
        } else {
            item = (Item) convertView.getTag();
        }
        item.btnUnit.setTag(position);
        item.etNum.setTag(position);
        item.btnUnit.setOnClickListener(this.onClickListener);
        item.tvBarcode.setTag(position);
        item.tvBarcode.setOnClickListener(onBarcodeListener);
        item.etNum.addTextChangedListener(new NumWatcher(item));
        item.setValue(listItems.get(position));
        return convertView;
    }

    class NumWatcher implements TextWatcher {
        private Item item;

        NumWatcher(Item arg2) {
            super();
            this.item = arg2;
        }

        public void afterTextChanged(Editable arg5) {
            listItems.get(Integer.parseInt(item.etNum.getTag().toString())).setSalenum(Utils.getDouble(arg5.toString()));
        }

        public void beforeTextChanged(CharSequence arg1, int arg2, int arg3, int arg4) {
        }

        public void onTextChanged(CharSequence arg1, int arg2, int arg3, int arg4) {
        }
    }
//    DocUtils
    public class Item {
        private Button btnUnit;
        private EditText etNum;
        private TextView tvBarcode;
        private TextView tvName;

        Item(View view) {
            super();
            this.tvName = view.findViewById(R.id.tvName);
            this.tvBarcode = view.findViewById(R.id.tvBarcode);
            this.btnUnit = view.findViewById(R.id.btnUnit);
            this.etNum = view.findViewById(R.id.etNum);
        }


        public void setValue(FieldSaleItemSource itemSource) {
            this.tvName.setText(itemSource.getGoodsname());
            this.tvBarcode.setText(itemSource.getBarcode());
            this.btnUnit.setText(itemSource.getSaleunitname());
            String v0 = itemSource.getSalenum() == 0 ? "" : String.valueOf(itemSource.getSalenum());
            etNum.setText(v0);
        }
    }
}
