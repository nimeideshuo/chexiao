package com.sunwuyou.swymcx.print;

import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.model.FieldSaleForPrint;
import com.sunwuyou.swymcx.model.FieldSaleItemForPrint;
import com.sunwuyou.swymcx.utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on
 * 2018/8/6.
 * content
 */
public class PrintMode3 extends PrintMode {
    public PrintMode3(List<HashMap<String, String>> arg5) {
        super(arg5);
    }

    protected boolean print() throws IOException {
        PrintUtils.setOutputStream(getOutputStream());
        PrintUtils.selectCommand(PrintUtils.RESET);
        PrintUtils.selectCommand(PrintUtils.LINE_SPACING_DEFAULT);
        headPrint();
        PrintUtils.printText("--------------------------------\n");
        PrintUtils.selectCommand(PrintUtils.BOLD);
        PrintUtils.printText(PrintUtils.printThreeData("商品名称", "", "库存\n"));
        PrintUtils.printText("--------------------------------\n");
        PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);
        printItem();
        PrintUtils.printText("\n\n\n\n\n");
        return true;
    }

    private void headPrint() {
        PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
        FieldSaleForPrint forPrint = getFieldSaleForPrint();
        PrintUtils.printText(PrintUtils.printTwoData("开单人:", forPrint.getBuildername() + "\n"));
        PrintUtils.printText(PrintUtils.printTwoData("开单时间:", forPrint.getBuildtime() + "\n"));
    }

    private void printItem() {
        for (FieldSaleItemForPrint item : getFieldSaleItemForPrint()) {
            PrintUtils.printText(PrintUtils.printTwoData(item.getGoodsname(),item.getBigStockNumber() + "\n"));
        }
    }

    private void printItem(FieldSaleItemForPrint item) throws IOException {
        this.print_base(print_left(item.getGoodsname()), 15, item.getBigStockNumber());
        this.enter();
    }

}
