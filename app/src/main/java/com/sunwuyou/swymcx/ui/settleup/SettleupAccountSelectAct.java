package com.sunwuyou.swymcx.ui.settleup;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.AccountDAO;
import com.sunwuyou.swymcx.dao.OtherSettleUpItemDAO;
import com.sunwuyou.swymcx.dao.SettleUpDAO;
import com.sunwuyou.swymcx.model.Account;
import com.sunwuyou.swymcx.model.OtherSettleUpItem;
import com.sunwuyou.swymcx.model.SettleUp;
import com.sunwuyou.swymcx.utils.ClickUtils;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.view.EditButtonView;

import java.util.List;

/**
 * Created by liupiao on
 * 2018/8/1.
 * content
 */
public class SettleupAccountSelectAct extends BaseHeadActivity implements View.OnClickListener {
    private Account account;
    private List<Account> accounts;
    private Button btnSaveAccount;
    private Button btnSettleupAcount;
    private EditButtonView etMoney;
    private boolean isupdate;
    private OtherSettleUpItem otherSettleUpItem;
    private SettleUp settleUp;

    @Override
    public int getLayoutID() {
        return R.layout.dia_settleup_account;
    }

    @Override
    public void initView() {
        this.btnSettleupAcount = this.findViewById(R.id.btnSettleupAcount);
        this.btnSaveAccount = this.findViewById(R.id.btnSaveAccount);
        this.etMoney = this.findViewById(R.id.etMoney);
        //        this.etMoney.setMode(1);
        //        this.etMoney.setDecNum(2);
        this.otherSettleUpItem = (OtherSettleUpItem) this.getIntent().getSerializableExtra("othersettleupitem");
        this.settleUp = new SettleUpDAO().getSettleUp(this.getIntent().getLongExtra("settleupid", -1));
        this.btnSettleupAcount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg6) {
                SettleupAccountSelectAct.this.startActivityForResult(new Intent().setClass(SettleupAccountSelectAct.this, SettleupAccountAct.class).putExtra("settleupid", SettleupAccountSelectAct.this.settleUp.getId()), 0);
            }
        });
        this.btnSaveAccount.setOnClickListener(this);
        if (this.otherSettleUpItem != null) {
            this.btnSettleupAcount.setText(this.otherSettleUpItem.getAccountname());
            this.etMoney.setText(String.valueOf(this.otherSettleUpItem.getAmount()));
            this.account = new Account();
            this.account.setAid(this.otherSettleUpItem.getAccountid());
            this.account.setAname(this.otherSettleUpItem.getAccountname());
            this.isupdate = true;
        }
        this.loadData();
    }

    private void loadData() {
        AccountDAO v1 = new AccountDAO();
        String v0 = this.settleUp.getType().equals("64") ? "404" : "504";
        this.accounts = v1.getAccounts(v0);
        if (this.accounts == null || this.accounts.size() == 0) {
            PDH.showMessage("无可用项目");
            this.finish();
        }
        if (this.otherSettleUpItem == null) {
            this.account = this.accounts.get(0);
            this.btnSettleupAcount.setText(this.account.getAname());
        }
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    this.account = (Account) data.getSerializableExtra("account");
                    this.btnSettleupAcount.setText(this.account.getAname());
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        boolean v1;
        double amount;
        if (!ClickUtils.isFastDoubleClick()) {
            try {
                amount = Double.parseDouble(this.etMoney.getText().toString());
            } catch (Exception e) {
                amount = 0;
                e.printStackTrace();
            }
            OtherSettleUpItem v4 = new OtherSettleUpItem();
            v4.setOthersettleupid(this.settleUp.getId());
            v4.setAccountid(this.account.getAid());
            v4.setAccountname(this.account.getAname());
            v4.setAmount(amount);
            if (this.isupdate) {
                v1 = new OtherSettleUpItemDAO().update(this.otherSettleUpItem.getSerialid(), v4);
            } else if (new OtherSettleUpItemDAO().save(v4) != -1) {
                v1 = true;
            } else {
                v1 = false;
            }

            if (v1) {
                this.finish();
            } else {
                PDH.showFail("操作失败");
            }
        }
    }

    @Override
    public void setActionBarText() {
        setTitle("项目");
    }


}
