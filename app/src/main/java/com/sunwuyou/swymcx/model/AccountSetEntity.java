package com.sunwuyou.swymcx.model;

/**
 * Created by admin
 * 2018/7/9.
 * content
 */

public class AccountSetEntity {
    public String getDatabase() {
        return database == null ? "" : database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String database;
    private int id;
    private String name;
}
