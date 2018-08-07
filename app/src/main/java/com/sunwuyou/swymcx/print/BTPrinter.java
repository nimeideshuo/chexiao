package com.sunwuyou.swymcx.print;

/**
 * Created by admin on
 * 2018/8/6.
 * content
 */
public class BTPrinter {
    private String address;
    private String name;

    public BTPrinter(String arg1, String arg2) {
        super();
        this.name = arg1;
        this.address = arg2;
    }

    public BTPrinter() {
        super();
    }

    public boolean equals(BTPrinter arg4) {
        return this.address.equals(arg4.getAddress());
    }

    public String getAddress() {
        return this.address;
    }

    public String getName() {
        return this.name;
    }

    public void setAddress(String arg1) {
        this.address = arg1;
    }

    public void setName(String arg1) {
        this.name = arg1;
    }

    public String toString() {
        return String.valueOf(this.name) + "\n" + this.address;
    }

}
