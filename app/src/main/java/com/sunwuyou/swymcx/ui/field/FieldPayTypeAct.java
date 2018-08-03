package com.sunwuyou.swymcx.ui.field;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.dao.FieldSaleItemDAO;
import com.sunwuyou.swymcx.dao.FieldSalePayTypeDAO;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.model.FieldSalePayType;
import com.sunwuyou.swymcx.utils.ClickUtils;
import com.sunwuyou.swymcx.utils.MLog;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.EditButtonView;

import java.util.List;

/**
 * Created by admin
 * 2018/7/25.
 * content
 */

public class FieldPayTypeAct extends BaseHeadActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private FieldSale fieldSale;
    private ListView listview;
    private Button btnSave;
    private EditButtonView etPreference;
    private TextView tvSubtotal;
    private TextView tvReceiveableprice;
    private double subtotal;
    private ItemAdapter adapter;
    private List<FieldSalePayType> fieldSalePayTypes;

    @Override
    public int getLayoutID() {
        return R.layout.settup_pay;
    }

    @Override
    public void initView() {
        setTitleRight("保存", null);
        fieldSale = new FieldSaleDAO().getFieldsale(this.getIntent().getLongExtra("fieldsaleid", -1));
        listview = this.findViewById(R.id.listview);
        btnSave = this.findViewById(R.id.btnSave);
        this.btnSave.setVisibility(View.VISIBLE);
        this.btnSave.setOnClickListener(this);
        etPreference = this.findViewById(R.id.etPreference);
        this.etPreference.setOnFocusChangeListener(this);
        EditButtonView v1 = this.etPreference;
        String v0 = this.fieldSale.getPreference() == 0 ? "" : String.valueOf(this.fieldSale.getPreference());
        v1.setText(v0);
        if (this.fieldSale.getStatus() == 2) {
            this.btnSave.setVisibility(View.GONE);
            this.etPreference.setEnabled(false);
        }
        this.tvSubtotal = this.findViewById(R.id.tvSubtotal);
        tvReceiveableprice = this.findViewById(R.id.tvReceiveableprice);
        subtotal = new FieldSaleItemDAO().getDocSalePrice(this.fieldSale.getId()) - new FieldSaleItemDAO().getDocCancelPrice(this.fieldSale.getId());
        this.tvSubtotal.setText(Utils.getRecvableMoney(this.subtotal));
        this.tvReceiveableprice.setText(Utils.getRecvableMoney(this.subtotal - this.fieldSale.getPreference()));
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        if (!ClickUtils.isFastDoubleClick()) {
            for (int i = 0; i < listview.getCount(); i++) {
                EditButtonView etPayAmount = listview.getChildAt(i).findViewById(R.id.etPayAmount);
                this.fieldSalePayTypes.get(i).setAmount(Utils.getDouble(etPayAmount.getText().toString()));
                new FieldSalePayTypeDAO().update(this.fieldSalePayTypes.get(i));
            }
            new FieldSaleDAO().updateDocValue(this.fieldSale.getId(), "preference", this.etPreference.getText().toString());
            this.finish();
        }

    }


    @Override
    public void initData() {
        fieldSalePayTypes = new FieldSalePayTypeDAO().queryPayTypes(this.fieldSale.getId());
        adapter = new ItemAdapter(this);
        this.adapter.setData(this.fieldSalePayTypes);
        this.listview.setAdapter(this.adapter);
    }

    @Override
    public void onClick(View v) {
        if (!ClickUtils.isFastDoubleClick()) {
            for (int i = 0; i < listview.getCount(); i++) {
                EditButtonView etPayAmount = listview.getChildAt(i).findViewById(R.id.etPayAmount);
                this.fieldSalePayTypes.get(i).setAmount(Utils.getDouble(etPayAmount.getText().toString()));
                new FieldSalePayTypeDAO().update(this.fieldSalePayTypes.get(i));
            }
            new FieldSaleDAO().updateDocValue(this.fieldSale.getId(), "preference", this.etPreference.getText().toString());
            this.finish();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            this.tvReceiveableprice.setText(String.valueOf(this.subtotal - Utils.getDouble(this.etPreference.getText().toString())));
        }
    }

    @Override
    public void setActionBarText() {
        super.setActionBarText();
        setTitle("单据收款");
    }

    private class ItemAdapter extends BaseAdapter {
        Context context;
        private List<FieldSalePayType> payTypes;

        ItemAdapter(Context context) {
            this.context = context;
        }

        public void setData(List<FieldSalePayType> payTypes) {
            this.payTypes = payTypes;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return this.payTypes.size();
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
            SettleItem v0_1;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_settleup_pay, null);
                v0_1 = new SettleItem(convertView);
                convertView.setTag(v0_1);
            } else {
                v0_1 = (SettleItem) convertView.getTag();
            }
            v0_1.setValue(this.payTypes.get(position));
            return convertView;
        }

        class SettleItem {
            private EditButtonView etPayAmount;
            private TextView tvPaytypeName;

            SettleItem(View arg4) {
                super();
                this.tvPaytypeName = arg4.findViewById(R.id.tvPaytypeName);
                this.etPayAmount = arg4.findViewById(R.id.etPayAmount);
                this.etPayAmount.setDecNum(Utils.NUMBER_DEC);
                this.etPayAmount.setMode(1);
                if (fieldSale.getStatus() == 2) {
                    this.etPayAmount.setEnabled(false);
                }
            }

            public void setValue(FieldSalePayType arg7) {
                this.tvPaytypeName.setText(arg7.getPaytypename());
                String v0 = arg7.getAmount() == 0 ? "" : String.valueOf(arg7.getAmount());
                etPayAmount.setText(v0);
                if (fieldSale.getStatus() == 2) {
                    this.etPayAmount.setEnabled(false);
                }
            }
        }
    }
}
