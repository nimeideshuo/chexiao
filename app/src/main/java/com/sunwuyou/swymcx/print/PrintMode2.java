package com.sunwuyou.swymcx.print;

import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.model.FieldSaleItemForPrint;
import com.sunwuyou.swymcx.utils.Utils;

import java.io.IOException;
import java.util.List;

/**
 * Created by admin on
 * 2018/8/6.
 * content
 */
public class PrintMode2 extends PrintMode {
    public PrintMode2(List arg5) {
        super(arg5);
    }

    protected boolean print() throws IOException {
        this.printHead();
        this.println("-------------------------------");
        this.print_base(this.print_left("商品名称"), 22, "数量");
        this.print_base(22, 32, "小计");
        this.smallText();
        int v0;
        for (v0 = 0; v0 < this.datainfo.size(); ++v0) {
            this.printItem(this.datainfo.get(v0));
        }

        this.normalText();
        this.println("-------------------------------");
        this.printFoot();
        this.tear_off();
        return true;
    }


    private void printItem(FieldSaleItemForPrint arg15) throws IOException {
        int v13 = 22;
        String v7 = "";
        if (!arg15.getItemtype().equals("销") && !"销售退货单".equals(this.docInfo.getDoctype())) {
            v7 = "[" + arg15.getItemtype() + "]";
        }

        String v3 = String.valueOf(v7) + arg15.getGoodsname();
        String v6 = Utils.getNumber(arg15.getNum());
        String v8 = Utils.getSubtotalMoney(arg15.getDiscountsubtotal());
        if ("0".equals(new AccountPreference().getValue("clearlastzero", "0"))) {
            v6 = Utils.cutLastZero(v6);
            v8 = Utils.cutLastZero(v8);
        }

        if (("0".equals(new AccountPreference().getValue("minustuihuo", "0"))) && ((arg15.getItemtype().equals("退")) || (arg15.getItemtype().equals("入"))) && !"销售退货单".equals(this.docInfo.getDoctype())) {
            v6 = "-" + Utils.cutLastZero(v6);
            v8 = "-" + Utils.cutLastZero(v8);
        }

        v6 = String.valueOf(v6) + arg15.getUnitname();
        int v5 = 0;
        int v10 = v3.length() / 8;
        int v9 = v3.length() % 8 == 0 ? 0 : 1;
        int v0 = v10 + v9;

        for (int i = 0; i < v0; i++) {
            int v1 = (i + 1) * 8;
            if (v1 > v3.length()) {
                v1 = v3.length();
            }
            v5 = this.print_left(v3.substring(i * 8, v1));
            if (i != v0 - 1) {
                this.enter();
            }
        }
        this.print_base(v5, v13, v6);
        this.print_base(v13, 32, v8);
        this.enter();
    }

}
