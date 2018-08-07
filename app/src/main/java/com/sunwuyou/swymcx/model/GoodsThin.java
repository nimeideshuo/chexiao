package com.sunwuyou.swymcx.model;

import java.io.Serializable;

public class GoodsThin implements Serializable {


	private String barcode;
	private String biginitnumber;
	private String bigstocknumber;
	private String id;
	private double initnumber;
	private boolean isusebatch;
	private String name;
	private String pinyin;
	private static final long serialVersionUID = 1;
	private String specification;
	private double stocknumber;


	public GoodsThin() {
		super();
	}

	public String getBarcode() {
		return this.barcode;
	}

	public String getBiginitnumber() {
		return this.biginitnumber;
	}

	public String getBigstocknumber() {
		return this.bigstocknumber;
	}

	public String getId() {
		return this.id;
	}

	public double getInitnumber() {
		return this.initnumber;
	}

	public boolean getIsusebatch() {
		return this.isusebatch;
	}

	public String getName() {
		return this.name;
	}

	public String getPinyin() {
		return this.pinyin;
	}

	public static long getSerialversionuid() {
		return 1;
	}

	public String getSpecification() {
		return this.specification;
	}

	public double getStocknumber() {
		return this.stocknumber;
	}

	public void setBarcode(String arg1) {
		this.barcode = arg1;
	}

	public void setBiginitnumber(String arg1) {
		this.biginitnumber = arg1;
	}

	public void setBigstocknumber(String arg1) {
		this.bigstocknumber = arg1;
	}

	public void setId(String arg1) {
		this.id = arg1;
	}

	public void setInitnumber(double arg1) {
		this.initnumber = arg1;
	}

	public void setIsusebatch(boolean arg1) {
		this.isusebatch = arg1;
	}

	public void setName(String arg1) {
		this.name = arg1;
	}

	public void setPinyin(String arg1) {
		this.pinyin = arg1;
	}

	public void setSpecification(String arg1) {
		this.specification = arg1;
	}

	public void setStocknumber(double arg1) {
		this.stocknumber = arg1;
	}


}