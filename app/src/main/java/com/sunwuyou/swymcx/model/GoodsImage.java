package com.sunwuyou.swymcx.model;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class GoodsImage {
    private String goodsid;
    private String imagepath;
    private boolean isgot;
    private long serialid;

    public String getGoodsid() {
        return goodsid == null ? "" : goodsid;
    }

    public void setGoodsId(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getImagePath() {
        return imagepath == null ? "" : imagepath;
    }

    public void setImagePath(String imagepath) {
        this.imagepath = imagepath;
    }

    public boolean isgot() {
        return isgot;
    }

    public void setIsGot(boolean isgot) {
        this.isgot = isgot;
    }

    public long getSerialid() {
        return serialid;
    }

    public void setSerialid(long serialid) {
        this.serialid = serialid;
    }
}
