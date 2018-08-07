package com.sunwuyou.swymcx.print;

import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.model.FieldSaleItemForPrint;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;

import java.io.IOException;
import java.util.List;

/**
 * Created by admin on
 * 2018/8/6.
 * content
 */
public class PrintMode1 extends PrintMode {
    public PrintMode1(List arg5) {
        super(arg5);
    }

    protected boolean print() throws IOException {
        this.printHead();
        this.println("-------------------------------");
        this.print_base(this.print_left("   "), 12, "数量");
        this.print_base(12, 22, "单价");
        this.print_base(22, 32, "小计");
        this.enter();
        for (int i = 0; i < datainfo.size(); i++) {
            this.printItem(datainfo.get(i));
        }
        this.println("-------------------------------");
        this.printFoot();
        this.tear_off();
        return true;
    }

    private void printItem(FieldSaleItemForPrint arg13) throws IOException {
        int v11 = 22;
        int v10 = 12;
        String v3 = "";
        if(!arg13.getItemtype().equals("销") && !"销售退货单".equals(this.docInfo.getDoctype())) {
            v3 = "[" + arg13.getItemtype() + "]";
        }

        String v0 = String.valueOf(v3) + arg13.getGoodsname();
        if(!"0".equals(new AccountPreference().getValue("printbarcode", "0"))) {
            if(!TextUtils.isEmpty(arg13.getBarcode())) {
                v0 = String.valueOf(v0) + "\n" + arg13.getBarcode();
                if(!TextUtils.isEmpty(arg13.getRemark())) {
                    v0 = String.valueOf(v0) + "，" + arg13.getRemark();
                }
            }
            else if(!TextUtils.isEmpty(arg13.getRemark())) {
                v0 = String.valueOf(v0) + "\n" + arg13.getRemark();
            }
        }

        String v2 = Utils.getNumber(arg13.getNum());
        String v4 = Utils.getSubtotalMoney(arg13.getPrice());
        String v5 = Utils.getSubtotalMoney(arg13.getDiscountsubtotal());
        if("0".equals(new AccountPreference().getValue("clearlastzero", "0"))) {
            v2 = Utils.cutLastZero(v2);
            v4 = Utils.cutLastZero(v4);
            v5 = Utils.cutLastZero(v5);
        }

        if(("0".equals(new AccountPreference().getValue("minustuihuo", "0"))) && ((arg13.getItemtype().equals("退")) || (arg13.getItemtype().equals("入"))) && !"销售退货单".equals(this.docInfo.getDoctype())) {
            v2 = "-" + Utils.cutLastZero(v2);
            v5 = "-" + Utils.cutLastZero(v5);
        }

        v2 = String.valueOf(v2) + arg13.getUnitname();
        this.print_left(v0);
        this.enter();
        this.print_base(this.print_left("   "), v10, v2);
        this.print_base(v10, v11, v4);
        this.print_base(v11, 32, v5);
        this.enter();
    }

}
