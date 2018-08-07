package com.sunwuyou.swymcx.response;

/**
 * Created by admin
 * 2018/7/20.
 * content
 */

public class IDNameEntity {
    private String id;
    private String name;

    public IDNameEntity() {
    }

    public IDNameEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
