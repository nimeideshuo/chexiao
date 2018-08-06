package com.sunwuyou.swymcx.print;

import com.sunwuyou.swymcx.model.FieldSaleForPrint;
import com.sunwuyou.swymcx.model.FieldSaleItemForPrint;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liupiao on
 * 2018/8/6.
 * content
 */
public class PrintData {
    private List<HashMap<String, String>> data;
    private HashMap<String, String> info;

    public PrintData() {
        super();
    }

    public List getData() {
        return this.data;
    }

    public HashMap getInfo() {
        return this.info;
    }

    public List getTestData() {
        ArrayList<FieldSaleItemForPrint> v1 = new ArrayList<>();
        FieldSaleItemForPrint v0 = new FieldSaleItemForPrint();
        v0.setItemtype("销");
        v0.setGoodsid("00001");
        v0.setGoodsname("电视机");
        v0.setBarcode("6000007890123");
        v0.setSpecification("1台");
        v0.setUnitname("台");
        v0.setNum(10);
        v0.setPrice(1200);
        v0.setDiscountratio(1);
        v0.setDiscountsubtotal(12000);
        v0.setRemark("测试电视机");
        ((List)v1).add(v0);
        v0 = new FieldSaleItemForPrint();
        v0.setItemtype("赠");
        v0.setGoodsid("00002");
        v0.setGoodsname("空调");
        v0.setBarcode("6000009012345");
        v0.setSpecification("1台");
        v0.setUnitname("台");
        v0.setNum(1);
        v0.setPrice(0);
        v0.setDiscountratio(1);
        v0.setDiscountsubtotal(0);
        v0.setRemark("测试电视机");
        ((List)v1).add(v0);
        return v1;
    }

    public FieldSaleForPrint getTestInfo() {
        FieldSaleForPrint v0 = new FieldSaleForPrint();
        v0.setDoctype("销售单");
        v0.setShowid("140101001");
        v0.setCustomername("某某超市");
        v0.setDepartmentname("销售部");
        v0.setBuildername("张三");
        v0.setBuildtime("2014-01-01 15:27:22");
        v0.setSumamount("合计:" + Utils.getSubtotalMoney(12000));
        v0.setReceivable("应收:" + Utils.getSubtotalMoney(12000));
        v0.setReceived("已收:" + Utils.getSubtotalMoney(2000));
        v0.setNum("数量:" + String.valueOf(11));
        v0.setPreference("优惠:" + String.valueOf(0));
        return v0;
    }

    public HashMap<String,String> getTestItem(String arg3, String arg4, String arg5, String arg6) {
        HashMap<String,String> v0 = new HashMap<>();
        v0.put("name", arg3);
        v0.put("number", arg4);
        v0.put("subtotal", arg6);
        v0.put("price", arg5);
        return v0;
    }

    public void setData(List<HashMap<String, String>> data) {
        this.data = data;
    }

    public void setInfo(HashMap<String,String> info) {
        this.info = info;
    }
}
