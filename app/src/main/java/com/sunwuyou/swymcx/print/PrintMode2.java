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
        enter();
        this.smallText();
        enter();
        for (int i = 0; i < datainfo.size(); i++) {
            this.printItem(this.datainfo.get(i));
        }
        this.normalText();
        this.println("-------------------------------");
        this.printFoot();
        this.tear_off();
        return true;
    }


    private void printItem(FieldSaleItemForPrint arg15) throws IOException {
        String v7 = "";
        if (!arg15.getItemtype().equals("销") && !"销售退货单".equals(this.docInfo.getDoctype())&&!arg15.getItemtype().isEmpty()) {
            v7 = "[" + arg15.getItemtype() + "]";
        }
        String goodsName = String.valueOf(v7) + arg15.getGoodsname();
        String goodsNumber = Utils.getNumber(arg15.getNum());
        String subtotalMoney = Utils.getSubtotalMoney(arg15.getDiscountsubtotal());
        if ("0".equals(new AccountPreference().getValue("clearlastzero", "0"))) {
            goodsNumber = Utils.cutLastZero(goodsNumber);
            subtotalMoney = Utils.cutLastZero(subtotalMoney);
        }

        if (("0".equals(new AccountPreference().getValue("minustuihuo", "0"))) && ((arg15.getItemtype().equals("退")) || (arg15.getItemtype().equals("入"))) && !"销售退货单".equals(this.docInfo.getDoctype())) {
            goodsNumber = "-" + Utils.cutLastZero(goodsNumber);
            subtotalMoney = "-" + Utils.cutLastZero(subtotalMoney);
        }

        goodsNumber = String.valueOf(goodsNumber) + arg15.getUnitname();
        int v5 = 0;
        int v10 = goodsName.length() / 8;
        int v9 = goodsName.length() % 8 == 0 ? 0 : 1;
        int v0 = v10 + v9;
        int defauleNameLength=17;
        for (int i = 0; i < v0; i++) {
//            int v1 = (i + 1) * 8;
//            if (v1 > goodsName.length()) {
//                v1 = goodsName.length();
//            }
            int v1=goodsName.length()>defauleNameLength?defauleNameLength:goodsName.length();
            v5 = this.print_left(goodsName.substring(0, v1));
            space(goodsName.length()>defauleNameLength?0:defauleNameLength-goodsName.length());
            if (i != v0 - 1) {
//                this.enter();
            }
            this.print_base(defauleNameLength, 15, goodsNumber);
            this.print_base(15, 25, subtotalMoney);
            this.enter();
        }

    }

}
