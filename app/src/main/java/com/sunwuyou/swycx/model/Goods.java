package com.sunwuyou.swycx.model;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class Goods {
    private String barcode;
    private String biginitnumber;
    private String bigstocknumber;
    private String getstocktime;
    private String goodsclassid;
    private String goodsclassname;
    private String id;
    private String initnumber;
    private boolean isusebatch;
    private String model;
    private String name;
    private String pinyin;
    private String salecue;
    private String specification;
    private String stocknumber;

    public String getBarcode() {
        return barcode == null ? "" : barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBiginitnumber() {
        return biginitnumber == null ? "" : biginitnumber;
    }

    public void setBiginitnumber(String biginitnumber) {
        this.biginitnumber = biginitnumber;
    }

    public String getBigstocknumber() {
        return bigstocknumber == null ? "" : bigstocknumber;
    }

    public void setBigstocknumber(String bigstocknumber) {
        this.bigstocknumber = bigstocknumber;
    }

    public String getGetstocktime() {
        return getstocktime == null ? "" : getstocktime;
    }

    public void setGetstocktime(String getstocktime) {
        this.getstocktime = getstocktime;
    }

    public String getGoodsclassid() {
        return goodsclassid == null ? "" : goodsclassid;
    }

    public void setGoodsclassid(String goodsclassid) {
        this.goodsclassid = goodsclassid;
    }

    public String getGoodsclassname() {
        return goodsclassname == null ? "" : goodsclassname;
    }

    public void setGoodsclassname(String goodsclassname) {
        this.goodsclassname = goodsclassname;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInitnumber() {
        return initnumber == null ? "" : initnumber;
    }

    public void setInitnumber(String initnumber) {
        this.initnumber = initnumber;
    }

    public boolean isusebatch() {
        return isusebatch;
    }

    public void setIsusebatch(boolean isusebatch) {
        this.isusebatch = isusebatch;
    }

    public String getModel() {
        return model == null ? "" : model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin == null ? "" : pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getSalecue() {
        return salecue == null ? "" : salecue;
    }

    public void setSalecue(String salecue) {
        this.salecue = salecue;
    }

    public String getSpecification() {
        return specification == null ? "" : specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getStocknumber() {
        return stocknumber == null ? "" : stocknumber;
    }

    public void setStocknumber(String stocknumber) {
        this.stocknumber = stocknumber;
    }
}
