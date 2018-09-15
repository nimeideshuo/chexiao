package com.sunwuyou.swymcx.model;


public class RespGoodsPriceEntity {

	private String batch;

	private String goodsid;

	private double price;

	private String pricesystemid;

	private String pricesystemname;

	private String unitid;

	private String unitname;

	public String getBatch() {
		return this.batch;
	}

	public String getGoodsid() {
		return this.goodsid;
	}

	public double getPrice() {
		return this.price;
	}

	public String getPricesystemid() {
		return this.pricesystemid;
	}

	
	public String getPricesystemname() {
		return this.pricesystemname;
	}

	
	public String getUnitid() {
		return this.unitid;
	}

	
	public String getUnitname() {
		return this.unitname;
	}

	public void setBatch(String paramString) {
		this.batch = paramString;
	}

	
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setPricesystemid(String pricesystemid) {
		this.pricesystemid = pricesystemid;
	}

	public void setPricesystemname(String pricesystemname) {
		this.pricesystemname = pricesystemname;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
}