package com.sunwuyou.swymcx.model;

import java.io.Serializable;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class Promotion implements Serializable {
    private static final long serialVersionUID = 1;
    private String begintime;
    private String endtime;
    private String id;
    private String name;

    public Promotion() {
        super();
    }

    public String getBeginTime() {
        return this.begintime;
    }

    public String getEndtime() {
        return this.endtime;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setBeginTime(String arg1) {
        this.begintime = arg1;
    }

    public void setEndtime(String arg1) {
        this.endtime = arg1;
    }

    public void setId(String arg1) {
        this.id = arg1;
    }

    public void setName(String arg1) {
        this.name = arg1;
    }
}
