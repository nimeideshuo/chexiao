package com.sunwuyou.swymcx.service;

import com.alibaba.fastjson.JSON;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.request.ReqCommonPara;
import com.sunwuyou.swymcx.request.ReqSynQueryCustomerPages;
import com.sunwuyou.swymcx.request.ReqSynQueryCustomerRecords;
import com.sunwuyou.swymcx.request.ReqSynQueryCustomerRecordsByID;
import com.sunwuyou.swymcx.request.ReqSynQueryPagesByID;
import com.sunwuyou.swymcx.request.ReqSynQueryRecords;
import com.sunwuyou.swymcx.request.ReqSynQueryRecordsByID;
import com.sunwuyou.swymcx.request.ReqSynQueryStockPages;
import com.sunwuyou.swymcx.request.ReqSynQueryStockRecords;
import com.sunwuyou.swymcx.request.ReqSynUpdateInfo;
import com.sunwuyou.swymcx.response.RespCustomerGoodsAndDocPages;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.utils.Utils_help;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by admin
 * 2018/7/15.
 * content
 */

public class ServiceSynchronize {

    private final int pagesize = 1000;
    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
    // public static String tables =
    // "log_deleterecord,sz_department,sz_warehouse,sz_paytype,cu_customer,cu_customertype,sz_region,sz_visitline,sz_goods,sz_goodsunit,sz_goodsimage";
    private String baseAddress = "synchronize";
    // private final int pagesizeForID = 2000;
    private long rversion = 0L;

    public ServiceSynchronize() {
    }

    public ServiceSynchronize(long rversion) {
        this.rversion = rversion;
    }

    public int getPageSize() {
        return 1000;
    }

    public int getPageSizeForID() {
        return 2000;
    }

    public long setRVersion() {
        return this.rversion;
    }

    public void setRVersion(long rversion) {
        this.rversion = rversion;
    }

    public RespCustomerGoodsAndDocPages syn_QueryCustomerGoodsAndDocPages(String id) {
        String url = Utils.getServiceAddress(this.baseAddress, "querycustomergoodsanddocpages");
        ReqSynQueryPagesByID v1 = new ReqSynQueryPagesByID();
        v1.setId(id);
        v1.setPagesize(1000);
        map.put("parameter", JSON.toJSONString(v1));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (!TextUtils.isEmptyS(infor)) {
            return JSON.parseObject(infor, RespCustomerGoodsAndDocPages.class);
        }
        return null;
    }


    // 查询客户记录
    public List<HashMap<String, String>> syn_QueryCustomerRecords(int pageindex) {
        String url = Utils.getServiceAddress(this.baseAddress, "querycustomerrecords");
        ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
        localReqSynQueryRecords.setPageindex(pageindex);
        localReqSynQueryRecords.setPagesize(pagesize);
        localReqSynQueryRecords.setRversion(this.rversion);
        map.put("parameter", JSON.toJSONString(localReqSynQueryRecords));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(infor)) {
            return JSONUtil.parse2ListMap(infor);
        }
        return null;
    }


    public List syn_QueryVisitJobRecords(int pageindex) {
        String url = Utils.getServiceAddress(this.baseAddress, "queryvisitjobrecords");
        ReqSynQueryRecords v1 = new ReqSynQueryRecords();
        v1.setPageindex(pageindex);
        v1.setPagesize(1000);
        v1.setRversion(this.rversion);
        map.put("parameter", JSON.toJSONString(v1));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(infor)) {
            return JSONUtil.parse2ListMap(infor);
        }
        return null;
    }

    public List<HashMap<String, String>> syn_QueryPriceSystemRecords(int paramInt) {
        String url = Utils.getServiceAddress(this.baseAddress, "querypricesystemrecords");
        ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
        localReqSynQueryRecords.setPageindex(paramInt);
        localReqSynQueryRecords.setPagesize(1000);
        localReqSynQueryRecords.setRversion(this.rversion);
        map.put("parameter", JSON.toJSONString(localReqSynQueryRecords));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(infor)) {
            return JSONUtil.parse2ListMap(infor);
        }
        return null;
    }

    //
//    // 查询经手人记录
//    public List<HashMap<String, String>> syn_QueryAllCustomerRecords(int paramInt) {
//        String url = Utils.getServiceAddress(this.baseAddress, "queryallcustomerrecords");
//        ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
//        localReqSynQueryRecords.setPageIndex(paramInt);
//        localReqSynQueryRecords.setPageSize(pagesize);
//        localReqSynQueryRecords.setRVersion(this.rversion);
//        map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
//        String infor = new Utils_help().getServiceInfor(url, map);
//        if (RequestHelper.isSuccess(infor)) {
//            return JSONUtil.parse2ListMap(infor);
//        }
//        return null;
//    }
//
    // 查询客户类型记录
    public List<HashMap<String, String>> syn_QueryCustomerTypeRecords(int pageIndex) {
        String url = Utils.getServiceAddress(this.baseAddress, "querycustomertyperecords");
        ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
        localReqSynQueryRecords.setPageindex(pageIndex);
        localReqSynQueryRecords.setPagesize(pagesize);
        localReqSynQueryRecords.setRversion(this.rversion);
        map.put("parameter", JSON.toJSONString(localReqSynQueryRecords));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(infor))
            return JSONUtil.parse2ListMap(infor);
        return null;
    }

    //
//    // 删除记录
    public List<HashMap<String, String>> syn_QueryDeleteRecordRecords(int paramInt) {
        String url = Utils.getServiceAddress(this.baseAddress, "querydeleterecordrecords");
        ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
        localReqSynQueryRecords.setPageindex(paramInt);
        localReqSynQueryRecords.setPagesize(pagesize);
        localReqSynQueryRecords.setRversion(this.rversion);
        map.put("parameter", JSON.toJSONString(localReqSynQueryRecords));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(infor)) {
            return JSONUtil.parse2ListMap(infor);
        }
        return null;
    }

    //
//    // 查询 部门记录
    public List<HashMap<String, String>> syn_QueryDepartmentRecords(int pageindex) {
        String url = Utils.getServiceAddress(this.baseAddress, "querydepartmentrecords");
        ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
        localReqSynQueryRecords.setPageindex(pageindex);
        localReqSynQueryRecords.setPagesize(pagesize);
        localReqSynQueryRecords.setRversion(this.rversion);
        map.put("parameter", JSON.toJSONString(localReqSynQueryRecords));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(infor))
            return JSONUtil.parse2ListMap(infor);
        return null;
    }

    //
//    /**
//     * 查询商品类别
//     *
//     * @param pageindex
//     */
//    public List<HashMap<String, String>> syn_QueryGoodsClassrecords(int pageindex) {
//        String url = Utils.getServiceAddress(this.baseAddress, "querygoodsclassrecords");
//        ReqSynQueryRecords queryRecords = new ReqSynQueryRecords();
//        queryRecords.setPageSize(pagesize);
//        queryRecords.setRVersion(rversion);
//        queryRecords.setPageIndex(pageindex);
//        map.put("parameter", JSONUtil.object2Json(queryRecords));
//        String serviceInfor = new Utils_help().getServiceInfor(url, map);
//        if (RequestHelper.isSuccess(serviceInfor))
//            return JSONUtil.parse2ListMap(serviceInfor);
//        return null;
//    }
//
//    /**
//     * 查询系统单价
//     *
//     * @param pageindex
//     * @return
//     */
    public List<HashMap<String, String>> syn_QueryPricesystem(int pageindex) {
        String url = Utils.getServiceAddress(this.baseAddress, "querypricesystemrecords");
        ReqSynQueryRecords queryRecords = new ReqSynQueryRecords();
        queryRecords.setPagesize(pagesize);
        queryRecords.setRversion(rversion);
        queryRecords.setPageindex(pageindex);
        map.put("parameter", JSON.toJSONString(queryRecords));
        String serviceInfor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(serviceInfor))
            return JSONUtil.parse2ListMap(serviceInfor);
        return null;
    }


    public String syn_QueryGoodsImage(String paramString) {
        String url = Utils.getServiceAddress(this.baseAddress, "querygoodsimage");
        ReqCommonPara localReqCommonPara = new ReqCommonPara();
        localReqCommonPara.setStringValue(paramString);
        map.put("parameter", JSONUtil.object2Json(localReqCommonPara));
        return new Utils_help().getServiceInfor(url, map);
    }

    //
    public List<HashMap<String, String>> syn_QueryGoodsImageRecords(int paramInt) {
        String url = Utils.getServiceAddress(this.baseAddress, "querygoodsimagerecords");
        ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
        localReqSynQueryRecords.setPageindex(paramInt);
        localReqSynQueryRecords.setPagesize(pagesize);
        localReqSynQueryRecords.setRversion(this.rversion);
        map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(infor)) {
            return JSONUtil.parse2ListMap(infor);
        }
        return null;
    }

    //
    public List<HashMap<String, String>> syn_QueryGoodsRecords(int paramInt) {
        String url = Utils.getServiceAddress(this.baseAddress, "querygoodsrecords");
        ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
        localReqSynQueryRecords.setPageindex(paramInt);
        localReqSynQueryRecords.setPagesize(pagesize);
        localReqSynQueryRecords.setRversion(this.rversion);
        map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(infor))
            return JSONUtil.parse2ListMap(infor);
        return null;
    }

    //
    public List<HashMap<String, String>> syn_QueryGoodsUnitRecords(int paramInt) {
        String url = Utils.getServiceAddress(this.baseAddress, "querygoodsunitrecords");
        ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
        localReqSynQueryRecords.setPageindex(paramInt);
        localReqSynQueryRecords.setPagesize(pagesize);
        localReqSynQueryRecords.setRversion(rversion);
        map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(infor)) {
            return JSONUtil.parse2ListMap(infor);
        }
        return null;
    }

    //
//    // 付款方式
    public List<HashMap<String, String>> syn_QueryPayTypeRecords(int paramInt) {
        String url = Utils.getServiceAddress(this.baseAddress, "querypaytyperecords");
        ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
        localReqSynQueryRecords.setPageindex(paramInt);
        localReqSynQueryRecords.setPagesize(pagesize);
        localReqSynQueryRecords.setRversion(this.rversion);
        map.put("parameter", JSON.toJSONString(localReqSynQueryRecords));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(infor))
            return JSONUtil.parse2ListMap(infor);
        return null;
    }

    public List<HashMap<String, String>> syn_QueryRegionRecords(int paramInt) {
        String url = Utils.getServiceAddress(this.baseAddress, "queryregionrecords");
        ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
        localReqSynQueryRecords.setPageindex(paramInt);
        localReqSynQueryRecords.setPagesize(pagesize);
        localReqSynQueryRecords.setRversion(this.rversion);
        map.put("parameter", JSON.toJSONString(localReqSynQueryRecords));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(infor))
            return JSONUtil.parse2ListMap(infor);
        return null;
    }


    // 查询升级返回值
    public List<ReqSynUpdateInfo> syn_QueryUpdateInfo(long rversion) {
        String url = Utils.getServiceAddress(this.baseAddress, "queryupdateinfo");
        ArrayList<ReqSynUpdateInfo> localArrayList = new ArrayList<ReqSynUpdateInfo>();
        localArrayList.add(new ReqSynUpdateInfo("log_deleterecord", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_unit", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_department", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_warehouse", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_paytype", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_account", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_pricesystem", 0L));
        localArrayList.add(new ReqSynUpdateInfo("cu_customertype", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_region", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_visitline", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_goods", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_goodsunit", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_goodsprice", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_goodsimage", 0L));
        localArrayList.add(new ReqSynUpdateInfo("rversion", rversion));
        localArrayList.add(new ReqSynUpdateInfo("pagesize", 1000L));
        map.put("parameter", JSONUtil.object2Json(localArrayList));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(infor)) {
            return JSON.parseArray(infor, ReqSynUpdateInfo.class);
        }
        return new ArrayList<>();
    }

    public List<HashMap<String, String>> syn_QueryAccountRecords(int index) {
        String url = Utils.getServiceAddress(this.baseAddress, "queryaccountrecords");
        ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
        localReqSynQueryRecords.setPageindex(index);
        localReqSynQueryRecords.setPagesize(1000);
        localReqSynQueryRecords.setRversion(this.rversion);
        map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(infor)) {
            return JSONUtil.parse2ListMap(infor);
        }
        return null;
    }


    //
    public List<HashMap<String, String>> syn_QueryVisitLineRecords(int paramInt) {
        String url = Utils.getServiceAddress(this.baseAddress, "queryvisitlinerecords");
        ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
        localReqSynQueryRecords.setPageindex(paramInt);
        localReqSynQueryRecords.setPagesize(pagesize);
        localReqSynQueryRecords.setRversion(this.rversion);
        map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(infor)) {
            return JSONUtil.parse2ListMap(infor);
        }
        return null;
    }

    //
    public List<HashMap<String, String>> syn_QueryWarehouseRecords(int pageindex) {
        String url = Utils.getServiceAddress(this.baseAddress, "querywarehouserecords");
        ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
        localReqSynQueryRecords.setPageindex(pageindex);
        localReqSynQueryRecords.setPagesize(pagesize);
        localReqSynQueryRecords.setRversion(rversion);
        map.put("parameter", JSON.toJSONString(localReqSynQueryRecords));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(infor)) {
            return JSONUtil.parse2ListMap(infor);
        }
        return null;
    }

    //
    public List<HashMap<String, String>> syn_QueryUnitrecords(int pageindex) {
        String url = Utils.getServiceAddress(this.baseAddress, "queryunitrecords");
        ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
        localReqSynQueryRecords.setPageindex(pageindex);
        localReqSynQueryRecords.setPagesize(pagesize);
        localReqSynQueryRecords.setRversion(rversion);
        map.put("parameter", JSON.toJSONString(localReqSynQueryRecords));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(infor)) {
            return JSONUtil.parse2ListMap(infor);
        }
        return null;
    }
    public List<HashMap<String, String>> syn_QueryGoodsPriceRecords(int pageindex) {
        String url = Utils.getServiceAddress(this.baseAddress, "querygoodspricerecords");
        ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
        localReqSynQueryRecords.setPageindex(pageindex);
        localReqSynQueryRecords.setPagesize(1000);
        localReqSynQueryRecords.setRversion(this.rversion);
        // map.put("usergoodsclassid", new
        // AccountPreference().getValue("user_goodsclassid"));
        map.put("parameter", JSONUtil.toJSONString(localReqSynQueryRecords));
        String serviceInfor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(serviceInfor)) {
            return JSONUtil.parse2ListMap(serviceInfor);
        }
        return null;
    }

    //
//    public List<HashMap<String, String>> syn_QueryCustomerGoodsRecords(String customerid, int pageindex) {
//        String url = Utils.getServiceAddress(this.baseAddress, "querycustomergoodsrecords");
//        ReqSynQueryRecordsByID recordsByID = new ReqSynQueryRecordsByID();
//        recordsByID.setId(customerid);
//        recordsByID.setPageIndex(pageindex);
//        recordsByID.setPageSize(pagesize);
//        map.put("parameter", JSONUtil.toJSONString(recordsByID));
//
//        String serviceInfor = new Utils_help().getServiceInfor(url, map);
//        if (RequestHelper.isSuccess(serviceInfor))
//            return JSONUtil.parse2ListMap(serviceInfor);
//        return null;
//    }
    public List<ReqSynUpdateInfo> syn_QueryStockPages(String warehouseid) {
        String url = Utils.getServiceAddress(this.baseAddress, "querystockpage");
        ReqSynQueryStockPages localReqSynQueryStockPages = new ReqSynQueryStockPages();
        localReqSynQueryStockPages.setPagesize(1000);
        localReqSynQueryStockPages.setWarehouseid(warehouseid);
        map.put("parameter", JSONUtil.toJSONString(localReqSynQueryStockPages));
        String serviceInfor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(serviceInfor)) {
            return JSONUtil.str2list(serviceInfor, ReqSynUpdateInfo.class);
        }
        return null;
    }

    public List<HashMap<String, String>> syn_QueryStockWarnRecords(String warehouseid, int index) {
        String url = Utils.getServiceAddress(this.baseAddress, "querystockwarnrecords");
        ReqSynQueryStockRecords v1 = new ReqSynQueryStockRecords();
        v1.setPageindex(index);
        v1.setPagesize(1000);
        v1.setWarehouseid(warehouseid);
        map.put("parameter", JSONUtil.toJSONString(v1));
        String serviceInfor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(serviceInfor)) {
            return JSONUtil.parse2ListMap(serviceInfor);
        }
        return null;
    }

    public List<HashMap<String, String>> syn_QueryGoodsBatchRecords(String warehouseid, int index) {
        String url = Utils.getServiceAddress(this.baseAddress, "querygoodsbatchrecords");
        ReqSynQueryStockRecords localReqSynQueryStockRecords = new ReqSynQueryStockRecords();
        localReqSynQueryStockRecords.setPageindex(index);
        localReqSynQueryStockRecords.setPagesize(1000);
        localReqSynQueryStockRecords.setWarehouseid(warehouseid);
        map.put("parameter", JSONUtil.toJSONString(localReqSynQueryStockRecords));
        String serviceInfor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(serviceInfor)) {
            return JSONUtil.parse2ListMap(serviceInfor);
        }
        return null;
    }

    public int syn_QueryCustomerIDPages() {
        String url = Utils.getServiceAddress(this.baseAddress, "querycustomeridpages");
        ReqSynQueryCustomerPages v3 = new ReqSynQueryCustomerPages();
        v3.setPageSize(2000);
        map.put("parameter", JSONUtil.toJSONString(v3));
        String serviceInfor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(serviceInfor)) {
            try {
                String pages = JSONUtil.parse2Map(serviceInfor).get("pages");
                return Integer.parseInt(pages);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return -1;
        }
        return -1;
    }

    public List<HashMap<String, String>> syn_QueryCustomerIDs(int index) {
        String url = Utils.getServiceAddress(this.baseAddress, "querycustomerids");
        ReqSynQueryCustomerRecords v1 = new ReqSynQueryCustomerRecords();
        v1.setPagesize(2000);
        v1.setPageindex(index);
        map.put("parameter", JSONUtil.toJSONString(v1));
        String serviceInfor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(serviceInfor)) {
            return JSONUtil.parse2ListMap(serviceInfor);
        } else {
            return null;
        }
    }

    public List<HashMap<String, String>> syn_QueryCustomerGoodsRecords(String id, int pageindex) {
        String url = Utils.getServiceAddress(this.baseAddress, "querycustomergoodsrecords");
        ReqSynQueryRecordsByID v1 = new ReqSynQueryRecordsByID();
        v1.setPageIndex(pageindex);
        v1.setPageSize(1000);
        v1.setId(id);
        map.put("parameter", JSONUtil.toJSONString(v1));
        String serviceInfor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(serviceInfor)) {
            return JSONUtil.parse2ListMap(serviceInfor);
        }
        return null;
    }

    public List<HashMap<String, String>> syn_QueryCustomerDocRecords(String id, int index) {
        String url = Utils.getServiceAddress(this.baseAddress, "querycustomerdocrecords");
        ReqSynQueryRecordsByID v1 = new ReqSynQueryRecordsByID();
        v1.setPageIndex(index);
        v1.setPageSize(1000);
        v1.setId(id);
        map.put("parameter", JSONUtil.toJSONString(v1));
        String serviceInfor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(serviceInfor)) {
            return JSONUtil.parse2ListMap(serviceInfor);
        }
        return null;
    }

    public List<HashMap<String, String>> syn_QueryCustomerRecordsByID(String arg8, boolean arg9, int arg10) {
        String url = Utils.getServiceAddress(this.baseAddress, "querycustomerrecordsbyid");
        ReqSynQueryCustomerRecordsByID v1 = new ReqSynQueryCustomerRecordsByID();
        v1.setCustomers(arg8);
        v1.setIsUpdate(arg9);
        v1.setMaxOrderNo(arg10);
        map.put("parameter", JSONUtil.toJSONString(v1));
        String serviceInfor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(serviceInfor)) {
            return JSONUtil.parse2ListMap(serviceInfor);
        }
        return null;
    }


}
