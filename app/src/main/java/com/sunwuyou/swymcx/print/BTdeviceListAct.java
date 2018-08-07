package com.sunwuyou.swymcx.print;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.model.FieldSaleForPrint;
import com.sunwuyou.swymcx.model.FieldSaleItemForPrint;
import com.sunwuyou.swymcx.ui.CustomerSearchOnLineAct;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.PDH;

import java.util.List;

/**
 * Created by admin on
 * 2018/8/6.
 * content
 */
public class BTdeviceListAct extends BaseHeadActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private BluetoothAdapter mBtAdapter;
    private int type;
    private FieldSaleForPrint doc;
    private ArrayAdapter<BTPrinter> devicesAdapter;
    private List<FieldSaleItemForPrint> items;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("android.bluetooth.device.action.FOUND".equals(intent.getAction())) {
                BluetoothDevice device = intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                BTPrinter printer = new BTPrinter(device.getName(), device.getAddress());
                devicesAdapter.add(printer);
            }
        }
    };

    @Override
    public int getLayoutID() {
        return R.layout.act_field_local_records;
    }

    @Override
    public void initView() {
        listView = findViewById(R.id.listView);
        this.listView.setLayoutParams(new ViewGroup.LayoutParams(-1, 600));
        this.setContentView(this.listView);
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        type = this.getIntent().getIntExtra("type", 0);
        doc = (FieldSaleForPrint) this.getIntent().getSerializableExtra("doc");
        items = JSONUtil.str2list(this.getIntent().getStringExtra("items"), FieldSaleItemForPrint.class);
        if (this.mBtAdapter == null) {
            PDH.showError("蓝牙不存在或未开启");
        } else {

            IntentFilter localIntentFilter = new IntentFilter("android.bluetooth.device.action.FOUND");
            registerReceiver(this.mReceiver, localIntentFilter);
            devicesAdapter = new ArrayAdapter<BTPrinter>(this, R.layout.item_textview);
            listView.setAdapter(devicesAdapter);
            this.listView.setOnItemClickListener(this);
            this.mBtAdapter.startDiscovery();
        }
    }
    protected void onDestroy() {
        this.mBtAdapter.cancelDiscovery();
        this.unregisterReceiver(this.mReceiver);
        super.onDestroy();
    }

    @Override
    public void initData() {

    }
    private void cancelScan() {
        if(this.mBtAdapter != null && (this.mBtAdapter.isDiscovering())) {
            this.mBtAdapter.cancelDiscovery();
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.cancelScan();
        BTPrinter v3 = this.devicesAdapter.getItem(position);
        new AccountPreference().savePrinter(v3);
        if(this.type == 2) {
            this.setResult(-1, new Intent());
            this.finish();
        }
        else {
            BTPrintHelper v2 = new BTPrintHelper(this);
            PrintMode v1 = PrintMode.getPrintMode();
            if(v1 == null) {
                PDH.showFail("未发现打印模版");
            }
            else {
                if(this.type == 1) {
                    PrintData v4 = new PrintData();
                    v1.setDatainfo(v4.getTestData());
                    v1.setDocInfo(v4.getTestInfo());
                }
                else {
                    v1.setDatainfo(this.items);
                    v1.setDocInfo(this.doc);
                    v2.setPrintOverCall(new BTPrintHelper.PrintOverCall() {
                        public void printOver() {
                            BTdeviceListAct.this.finish();
                            new FieldSaleDAO().updateDocValue(BTdeviceListAct.this.doc.getId(), "printnum", "1");
                        }
                    });
                }

                v2.setMode(v1);
                v2.print(((BTPrinter)v3));
            }
        }
    }
}
