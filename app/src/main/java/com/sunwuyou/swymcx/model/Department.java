package com.sunwuyou.swymcx.model;

import java.io.Serializable;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class Department implements Serializable {
    private String did;
    private String dname;

    public String getDid() {
        return did == null ? "" : did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getDname() {
        return dname == null ? "" : dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getWarehouseid() {
        return warehouseid == null ? "" : warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }

    public String getWarehousename() {
        return warehousename == null ? "" : warehousename;
    }

    public void setWarehousename(String warehousename) {
        this.warehousename = warehousename;
    }

    private String warehouseid;
    private String warehousename;

}
