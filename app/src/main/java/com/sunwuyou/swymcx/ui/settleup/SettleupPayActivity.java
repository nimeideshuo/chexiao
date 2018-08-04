package com.sunwuyou.swymcx.ui.settleup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.OtherSettleUpItemDAO;
import com.sunwuyou.swymcx.dao.SettleUpDAO;
import com.sunwuyou.swymcx.dao.SettleUpItemDAO;
import com.sunwuyou.swymcx.dao.SettleUpPayTypeDAO;
import com.sunwuyou.swymcx.model.SettleUp;
import com.sunwuyou.swymcx.model.SettleUpPayType;
import com.sunwuyou.swymcx.utils.ClickUtils;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.EditButtonView;

import java.util.List;

/**
 * Created by admin on
 * 2018/8/1.
 * content
 */
public class SettleupPayActivity extends BaseHeadActivity implements View.OnFocusChangeListener {

    private ListView listview;
    private SettleUp settleUp;
    private ItemAdapter adapter;
    private Button btnSave;
    private EditButtonView etPreference;
    private boolean isOther;
    private TextView tvReceiveableTitle;
    private TextView tvReceiveableprice;
    private TextView tvSubtotal;
    private List<SettleUpPayType> payItems;
    View.OnClickListener saveOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!ClickUtils.isFastDoubleClick()) {
                if (!SettleupPayActivity.this.isOther) {
                    String v3 = SettleupPayActivity.this.etPreference.getText().toString();
                    if (TextUtils.isEmptyS(v3)) {
                        v3 = "0.0";
                    }
                    if (!Utils.validNAMAndShow(v3, Utils.NUMBER_DEC, new double[0])) {
                        int v1;
                        for (v1 = 0; v1 < payItems.size(); ++v1) {
                            SettleUpPayType v2 = payItems.get(v1);
                            EditText edAm = listview.getChildAt(v1).findViewById(R.id.etPayAmount);
                            String v0 = edAm.getText().toString();
                            if (TextUtils.isEmptyS(v0)) {
                                v0 = "0.0";
                            }
                            v2.setAmount(Utils.getDouble(v0));
                            new SettleUpPayTypeDAO().update(v2.getId(), v2);
                        }

                        finish();
                    }
                    new SettleUpDAO().update(settleUp.getId(), "preference", v3);
                }

                for (int v1 = 0; v1 < payItems.size(); ++v1) {
                    SettleUpPayType v2 = payItems.get(v1);
                    EditText edAm = listview.getChildAt(v1).findViewById(R.id.etPayAmount);
                    String v0 = edAm.getText().toString();
                    if (TextUtils.isEmptyS(v0)) {
                        v0 = "0.0";
                    }
                    v2.setAmount(Utils.getDouble(v0));
                    new SettleUpPayTypeDAO().update(v2.getId(), v2);
                }
                finish();
            }
        }
    };
    private EditButtonView etPayAmount;

    @Override
    public int getLayoutID() {
        return R.layout.settup_pay;
    }

    @Override
    public void initView() {
        etPreference = this.findViewById(R.id.etPreference);
        listview = this.findViewById(R.id.listview);
        this.listview.requestFocus();
        settleUp = new SettleUpDAO().getSettleUp(this.getIntent().getLongExtra("settleupid", -1));
        this.isOther = !this.settleUp.getType().equals("63");
        this.etPreference.setDecNum(Utils.NUMBER_DEC);
        this.tvSubtotal = this.findViewById(R.id.tvSubtotal);
        this.tvReceiveableprice = this.findViewById(R.id.tvReceiveableprice);
        this.tvReceiveableTitle = this.findViewById(R.id.tvReceiveableTitle);
        double v0 = this.isOther ? new OtherSettleUpItemDAO().getOtherSettleupAmount(this.settleUp.getId()) : new SettleUpItemDAO().getSettleupAmount(this.settleUp.getId());
        double v2 = this.settleUp.getPreference();
        this.tvSubtotal.setText(Utils.getRecvableMoney(v0));
        this.tvReceiveableprice.setText(Utils.getRecvableMoney(v0 - v2));
        if (!this.isOther) {
            this.tvReceiveableTitle.setText("优惠后应收：");
        } else if (this.settleUp.getType().equals("64")) {
            this.tvReceiveableTitle.setText("应收：");
        } else {
            this.tvReceiveableTitle.setText("应付：");
        }

        EditButtonView v7 = this.etPreference;
        String v6 = this.settleUp.getPreference() == 0 ? "" : new StringBuilder(String.valueOf(this.settleUp.getPreference())).toString();
        v7.setText(v6);
        this.btnSave = this.findViewById(R.id.btnSave);
        this.btnSave.setOnClickListener(saveOnClickListener);
        if (this.isOther) {
            this.findViewById(R.id.linPreference).setVisibility(View.GONE);
            this.findViewById(R.id.linearDivider).setVisibility(View.GONE);
        }

        if (this.settleUp.getIsSubmit()) {
            this.etPreference.setEnabled(false);
            this.btnSave.setVisibility(View.GONE);
        }

        this.loadData();
        this.etPreference.setOnFocusChangeListener(this);
    }

    @Override
    public void initData() {

    }

    private void loadData() {
        payItems = new SettleUpPayTypeDAO().getPayTypes(this.settleUp.getId());
        if (this.payItems == null || this.payItems.size() == 0) {
            PDH.showMessage("无可用的付款方式");
            this.finish();
        } else {
            this.adapter = new ItemAdapter(this);
            this.adapter.setData(this.payItems);
            this.listview.setAdapter(this.adapter);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            double v2 = Utils.getDouble(this.etPreference.getText().toString());
            double v0 = this.settleUp.getType().equals("63") ? new SettleUpItemDAO().getSettleupAmount(this.settleUp.getId()) : new OtherSettleUpItemDAO().getOtherSettleupAmount(this.settleUp.getId());
            this.tvReceiveableprice.setText(Utils.getRecvableMoney(v0 - v2));
        }
    }

    public void onItemSelected(AdapterView arg3, View arg4, int arg5, long arg6) {
        this.listview.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        this.etPayAmount = this.findViewById(R.id.etPayAmount);
        this.etPayAmount.requestFocus();
    }

    public void onNothingSelected(AdapterView arg3) {
        this.listview.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
    }

    class ItemAdapter extends BaseAdapter {
        private List<SettleUpPayType> payTypes;

        public ItemAdapter(Context context) {
            super();
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

        public void setData(List<SettleUpPayType> payTypes) {
            this.payTypes = payTypes;
            this.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SettleItem v0;
            if (convertView == null) {
                convertView = LayoutInflater.from(SettleupPayActivity.this).inflate(R.layout.item_settleup_pay, null);
                v0 = new SettleItem(convertView);
                convertView.setTag(v0);
            } else {
                v0 = (SettleItem) convertView.getTag();
            }
            v0.setValue(this.payTypes.get(position));
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
            }

            public void setValue(SettleUpPayType arg7) {
                this.tvPaytypeName.setText(arg7.getPaytypename());
                String v0 = arg7.getAmount() == 0 ? "" : String.valueOf(arg7.getAmount());
                etPayAmount.setText(v0);
                if (settleUp.getIsSubmit()) {
                    this.etPayAmount.setEnabled(false);
                }
            }
        }
    }
}
