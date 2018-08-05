package com.sunwuyou.swymcx.response;

/**
 * Created by admin
 * 2018/8/5.
 * content
 */

public class RespGoodsBatchEntity {
    private String batch;
    private String bigstocknum;
    private String goodsid;
    private String productiondate;
    private double stocknum;
    private String warehouseid;
    private String warehousename;

    public RespGoodsBatchEntity() {
        super();
    }

    public RespGoodsBatchEntity(String arg1, String arg2, String arg3, String arg4, String arg5, double arg6, String arg8) {
        super();
        this.goodsid = arg1;
        this.batch = arg2;
        this.productiondate = arg3;
        this.stocknum = arg6;
        this.warehouseid = arg4;
        this.warehousename = arg5;
        this.bigstocknum = arg8;
    }

    public String getBatch() {
        return this.batch;
    }

    public String getBigstocknum() {
        return this.bigstocknum;
    }

    public String getGoodsid() {
        return this.goodsid;
    }

    public String getProductiondate() {
        return this.productiondate;
    }

    public double getStocknum() {
        return this.stocknum;
    }

    public String getWarehouseId() {
        return this.warehouseid;
    }

    public String getWarehouseName() {
        return this.warehousename;
    }

    public void setBatch(String arg1) {
        this.batch = arg1;
    }

    public void setBigstocknum(String arg1) {
        this.bigstocknum = arg1;
    }

    public void setGoodsid(String arg1) {
        this.goodsid = arg1;
    }

    public void setProductiondate(String arg1) {
        this.productiondate = arg1;
    }

    public void setStocknum(double arg1) {
        this.stocknum = arg1;
    }

    public void setWarehouseId(String arg1) {
        this.warehouseid = arg1;
    }

    public void setWarehouseName(String arg1) {
        this.warehousename = arg1;
    }

}
