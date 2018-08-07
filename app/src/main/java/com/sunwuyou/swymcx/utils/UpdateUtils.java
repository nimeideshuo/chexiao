package com.sunwuyou.swymcx.utils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.sunwuyou.swymcx.dao.CustomerDAO;
import com.sunwuyou.swymcx.dao.DBOpenHelper;
import com.sunwuyou.swymcx.dao.GoodsDAO;
import com.sunwuyou.swymcx.dao.GoodsImageDAO;
import com.sunwuyou.swymcx.dao.PromotionDAO;
import com.sunwuyou.swymcx.model.GoodsImage;
import com.sunwuyou.swymcx.request.ReqSynUpdateInfo;
import com.sunwuyou.swymcx.response.IDNameEntity;
import com.sunwuyou.swymcx.response.RespCustomerGoodsAndDocPages;
import com.sunwuyou.swymcx.service.ServiceSupport;
import com.sunwuyou.swymcx.service.ServiceSynchronize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin
 * 2018/7/15.
 * content
 */

public class UpdateUtils {
    public static final int AddAll = 0;
    public static final int AddCustomerID = 1;
    public static final int AddRegionID = 3;
    public static final int AddVisitLineID = 2;
    public static final int RefreshLocalALl = 4;


    public boolean executeCustomerUpdate(Handler paramHandler, int type, String id) {
        ServiceSynchronize v19 = new ServiceSynchronize(0L);
        CustomerDAO v4 = new CustomerDAO();
        ArrayList<String> v13 = new ArrayList<>();
        switch (type) {
            case 0:
                try {
                    int v18 = v19.syn_QueryCustomerIDPages();
                    for (int i = 1; i <= v18; i++) {
                        List<HashMap<String, String>> v11 = v19.syn_QueryCustomerIDs(i);
                        if (v11 == null) {
                            break;
                        }
                        for (int j = 0; j < v11.size(); j++) {
                            v13.add(v11.get(j).get("id"));
                        }

                    }
                } catch (NumberFormatException v7) {
                    paramHandler.sendMessage(paramHandler.obtainMessage(1, "客户信息同步失败，请重试"));
                    return false;
                }
                break;
            case 1:
                if (v4.isExists(id)) {
                    paramHandler.sendMessage(paramHandler.obtainMessage(1, "指定客户已存在"));
                    return false;
                }
                v13.add(id);
                break;
            case 2:
                //客户路线
                List<IDNameEntity> v15 = JSONUtil.str2list(new ServiceSupport().QueryVisitLineCustomers(id), IDNameEntity.class);
                if (v15 != null && v15.size() != 0) {
                    for (int i = 0; i < v15.size(); i++) {
                        String v5 = v15.get(i).getId();
                        if (v4.isExists(v5)) {
                            v15.remove(i);
                        } else {
                            v13.add(v5);
                        }
                    }
                } else {
                    paramHandler.sendMessage(paramHandler.obtainMessage(1, "指定路线无客户"));
                    return false;
                }
            case 3:
                //客户地区
                List<IDNameEntity> v14 = JSONUtil.str2list(new ServiceSupport().QueryRegionCustomers(id), IDNameEntity.class);
                if (v14 != null && v14.size() != 0) {
                    int v9 = 0;
                    while (true) {
                        if (v9 < v14.size()) {
                            String v5 = v14.get(v9).getId();
                            if (v4.isExists(v5)) {
                                v14.remove(v9);
                                --v9;
                            } else {
                                v13.add(v5);
                            }
                            ++v9;
                        } else {
                            break;
                        }
                    }
                } else {
                    paramHandler.sendMessage(paramHandler.obtainMessage(1, "指定区域无客户"));
                    return false;
                }
                break;

        }
        if (v13.size() != 0) {
            int v17 = v19.getPageSize();
            int v22 = v13.size() / v17;
            int v21_1 = v13.size() % v17 > 0 ? 1 : 0;
            int v20 = v22 + v21_1;
            //            if (v20 <= 0) {
            if (!this.executeCustomerGoodsAndDocUpdate(paramHandler, v13)) {
                paramHandler.sendMessage(paramHandler.obtainMessage(1, "客户信息同步失败，请重试"));
                return false;
            }
            if (!this.executePromotionUpdate(paramHandler, v13)) {
                paramHandler.sendMessage(paramHandler.obtainMessage(1, "客户信息同步失败，请重试"));
                return false;
            }
            //            }

            for (int i = 0; i < v20; i++) {
                int v3 = i * v17;
                int v8 = (i + 1) * v17 - 1;
                if (v8 >= v13.size()) {
                    v8 = v13.size() - 1;
                }
                String v6 = "";
                for (int v12 = v3; v12 <= v8; ++v12) {
                    v6 = String.valueOf(v6) + ",\'" + ((List) v13).get(v12) + "\'";
                }
                if (v6.length() > 0) {
                    v6 = v6.substring(1);
                }

                boolean v10 = false;
                if (type == 4) {
                    v10 = true;
                }
                // 第二个执行
                List<HashMap<String, String>> list = v19.syn_QueryCustomerRecordsByID(v6, v10, v4.getMaxOrderNo());
                if (list == null) {
                    break;
                }
                this.saveToLocalDB(list);

            }
            return true;
        }
        return true;
    }

    public boolean executePromotionUpdate(Handler arg26, List arg27) {

        int v15;
        int v7;
        int v3;
        ArrayList<String> v11 = new ArrayList<String>();
        ServiceSynchronize v21 = new ServiceSynchronize(0);
        int v14 = v21.getPageSize();
        int v24 = arg27.size() / v14;
        int v23 = arg27.size() % v14 > 0 ? 1 : 0;
        int v22 = v24 + v23;
        CustomerDAO v4 = new CustomerDAO();
        for (int v13 = 0; v13 < v22; ++v13) {
            v3 = v13 * v14;
            v7 = (v13 + 1) * v14 - 1;
            if (v7 >= arg27.size()) {
                v7 = arg27.size() - 1;
            }

            String v5 = "";
            int v10;
            for (v10 = v3; v10 <= v7; ++v10) {
                v5 = String.valueOf(v5) + ",\'" + arg27.get(v10) + "\'";
            }

            if (v5.length() > 0) {
                v5 = v5.substring(1);
            }
            v4.getPromotionID(v11, v5);
        }
        PromotionDAO v16 = new PromotionDAO();
        int v8;
        for (v8 = 0; v8 < v11.size(); ++v8) {
            if (v16.isExists(v11.get(v8))) {
                v11.remove(v8);
                --v8;
            }
        }

        if (v11.size() == 0) {
            return true;
        }


        return true;
    }


    public boolean executeGoodsStockUpdate(Handler paramHandler, String warehouseid) {
        ServiceSynchronize localServiceSynchronize = new ServiceSynchronize(0L);
        SwyUtils localObject = new SwyUtils();
        new GoodsDAO().clearStock();
        List<ReqSynUpdateInfo> localList1 = localServiceSynchronize.syn_QueryStockPages(warehouseid);
        if (localList1 == null) {
            paramHandler.sendMessage(paramHandler.obtainMessage(1, "服务器访问异常"));
            return false;
        }
        if (localObject.getSumPagesFromUpdateInfo(localList1) == 0) {
            paramHandler.sendMessage(paramHandler.obtainMessage(1, "无库存可以装车"));
            return false;
        }
        int i = 0;
        int sz_stockwarn = (int) localObject.getPagesFromUpdateInfo(localList1, "sz_stockwarn");
        if (sz_stockwarn > 0) {
            for (int j = 1; j <= sz_stockwarn; j++) {
                List<HashMap<String, String>> localList2 = localServiceSynchronize.syn_QueryStockWarnRecords(warehouseid, j);
                if (localList2 == null) {
                    return false;
                }
                saveToLocalDB(localList2);
                i++;
                paramHandler.sendEmptyMessage(i);
            }
        }

        int kf_goodsbatch = (int) localObject.getPagesFromUpdateInfo(localList1, "kf_goodsbatch");

        if (kf_goodsbatch > 0) {
            for (int j = 1; j <= kf_goodsbatch; j++) {
                List<HashMap<String, String>> localList2 = localServiceSynchronize.syn_QueryGoodsBatchRecords(warehouseid, j);
                if (localList2 == null) {
                    paramHandler.sendMessage(paramHandler.obtainMessage(1, "装货失败，请重试"));
                    return false;
                }
                saveToLocalDB(localList2);
                i++;
                paramHandler.sendEmptyMessage(i);
            }
        }
        return true;
    }

    public boolean executeUpdate(Handler paramHandler, List<ReqSynUpdateInfo> paramList, long rversion) {
        // SQL http 接口
        ServiceSynchronize ssy = new ServiceSynchronize(rversion);
        SwyUtils localSwyUtils = new SwyUtils();
        paramHandler.sendMessage(
                paramHandler.obtainMessage(-1, localSwyUtils.getSumPagesFromUpdateInfo(paramList)));

        int i = 0;
        int log_deleterecord = (int) localSwyUtils.getPagesFromUpdateInfo(paramList, "log_deleterecord");
        if (log_deleterecord > 0) {
            for (int j = 1; j <= log_deleterecord; j++) {
                List<HashMap<String, String>> localList2 = ssy.syn_QueryDeleteRecordRecords(j);
                if (localList2 == null) {
                    return false;
                }
                saveToLocalDB(localList2);
                i++;
                paramHandler.sendEmptyMessage(i);
            }
        }
        int unitPages = (int) localSwyUtils.getPagesFromUpdateInfo(paramList, "sz_unit");
        if (unitPages > 0) {
            for (int j = 1; j <= unitPages; j++) {
                List<HashMap<String, String>> listunit = ssy.syn_QueryUnitrecords(j);
                if (listunit == null) {
                    return false;
                }
                saveToLocalDB(listunit);
                i++;
                paramHandler.sendEmptyMessage(i);
            }
        }

        // 部门
        int m = (int) localSwyUtils.getPagesFromUpdateInfo(paramList, "sz_department");
        if (m > 0) {
            for (int j = 1; j <= m; j++) {
                List<HashMap<String, String>> localList2 = ssy.syn_QueryDepartmentRecords(j);
                if (localList2 == null) {
                    return false;
                }
                saveToLocalDB(localList2);
                i++;
                paramHandler.sendEmptyMessage(i);
            }

        }
        // 仓库
        int i1 = (int) localSwyUtils.getPagesFromUpdateInfo(paramList, "sz_warehouse");
        if (i1 > 0) {
            for (int j = 1; j <= i1; j++) {
                List<HashMap<String, String>> localList3 = ssy.syn_QueryWarehouseRecords(j);
                if (localList3 == null) {
                    return false;
                }
                saveToLocalDB(localList3);
                i++;
                paramHandler.sendEmptyMessage(i);
            }

        }
        // 支付
        int i3 = (int) localSwyUtils.getPagesFromUpdateInfo(paramList, "sz_paytype");
        if (i3 > 0) {
            for (int j = 1; j <= i3; j++) {
                List<HashMap<String, String>> localList4 = ssy.syn_QueryPayTypeRecords(j);
                if (localList4 == null) {
                    return false;
                }
                saveToLocalDB(localList4);
                i++;
                paramHandler.sendEmptyMessage(i);
            }

        }
        int i4 = (int) localSwyUtils.getPagesFromUpdateInfo(paramList, "sz_account");
        if (i4 > 0) {
            for (int j = 1; j <= i4; j++) {
                List<HashMap<String, String>> localList4 = ssy.syn_QueryAccountRecords(j);
                if (localList4 == null) {
                    return false;
                }
                saveToLocalDB(localList4);
                i++;
                paramHandler.sendEmptyMessage(i);
            }
        }
        // 所有客户 包括供应商 由于返回值 电话号码 返回值 NULL 放在前面防止客户 电话等信息被覆盖
        int i6 = (int) localSwyUtils.getPagesFromUpdateInfo(paramList, "sz_visitjob");
        if (i6 > 0) {
            for (int j = 1; j <= i6; j++) {
                List<HashMap<String, String>> localList5 = ssy.syn_QueryVisitJobRecords(j);
                if (localList5 == null) {
                    return false;
                }
                // setReplaceToUpdata(localList5);
                saveToLocalDB(localList5);
                i++;
                paramHandler.sendEmptyMessage(i);
            }
        }


        // 客户
        int i5 = (int) localSwyUtils.getPagesFromUpdateInfo(paramList, "cu_customer");
        if (i5 > 0) {
            for (int j = 1; j <= i5; j++) {
                List<HashMap<String, String>> localList5 = ssy.syn_QueryCustomerRecords(j);
                if (localList5 == null) {
                    return false;
                }
                saveToLocalDB(localList5);
                i++;
                paramHandler.sendEmptyMessage(i);
            }
        }


        int pricesystemPages = (int) localSwyUtils.getPagesFromUpdateInfo(paramList, "sz_pricesystem");
        if (pricesystemPages > 0) {
            for (int j = 1; j <= pricesystemPages; j++) {
                List<HashMap<String, String>> listClass = ssy.syn_QueryPricesystem(j);
                if (listClass == null) {
                    return false;
                }
                saveToLocalDB(listClass);
                i++;
                paramHandler.sendEmptyMessage(i);

            }
        }

        int i7 = (int) localSwyUtils.getPagesFromUpdateInfo(paramList, "cu_customertype");
        if (i7 > 0) {
            for (int j = 1; j <= i7; j++) {
                List<HashMap<String, String>> localList6 = ssy.syn_QueryCustomerTypeRecords(j);
                if (localList6 == null) {
                    return false;
                }
                saveToLocalDB(localList6);
                i++;
                paramHandler.sendEmptyMessage(i);
            }
        }

        int i9 = (int) localSwyUtils.getPagesFromUpdateInfo(paramList, "sz_region");
        if (i9 > 0) {
            for (int j = 1; j <= i9; j++) {
                List<HashMap<String, String>> localList7 = ssy.syn_QueryRegionRecords(j);
                if (localList7 == null) {
                    return false;
                }
                saveToLocalDB(localList7);
                i++;
                paramHandler.sendEmptyMessage(i);
            }
        }

        int i11 = (int) localSwyUtils.getPagesFromUpdateInfo(paramList, "sz_visitline");
        if (i11 > 0) {
            for (int j = 1; j <= i11; j++) {
                List<HashMap<String, String>> localList8 = ssy.syn_QueryVisitLineRecords(j);
                if (localList8 == null) {

                }
                saveToLocalDB(localList8);
                i++;
                paramHandler.sendEmptyMessage(i);

            }
        }


        int i13 = (int) localSwyUtils.getPagesFromUpdateInfo(paramList, "sz_goods");
        if (i13 > 0) {
            for (int j = 1; j <= i13; j++) {
                List<HashMap<String, String>> localList9 = ssy.syn_QueryGoodsRecords(j);
                if (localList9 == null) {
                    return false;
                }
                saveToLocalDB(localList9);
                i++;
                paramHandler.sendEmptyMessage(i);

            }
        }
        int i15 = (int) localSwyUtils.getPagesFromUpdateInfo(paramList, "sz_goodsunit");
        if (i15 > 0) {
            for (int j = 1; j <= i15; j++) {
                List<HashMap<String, String>> localList10 = ssy.syn_QueryGoodsUnitRecords(j);
                if (localList10 == null) {
                    return false;
                }
                saveToLocalDB(localList10);
                i++;
                paramHandler.sendEmptyMessage(i);

            }
        }


        int goodspriceindex = (int) localSwyUtils.getPagesFromUpdateInfo(paramList, "sz_goodsprice");
        if (goodspriceindex > 0) {
            for (int j = 1; j <= goodspriceindex; j++) {
                List<HashMap<String, String>> goodsRecords = ssy.syn_QueryGoodsPriceRecords(j);
                if (goodsRecords == null) {
                    return false;
                }
                this.saveToLocalDB(goodsRecords);
                i++;
                paramHandler.sendEmptyMessage(i);
            }
        }

        int v8 = (int) localSwyUtils.getPagesFromUpdateInfo(paramList, "sz_goodsimage");
        int v5 = 1;
        if (v5 <= v8) {
            List<HashMap<String, String>> records = ssy.syn_QueryGoodsImageRecords(v5);
            if (records == null) {
                return false;
            }
            this.saveToLocalDB(records);
            i++;
            paramHandler.sendEmptyMessage(i);
            ++v5;
            GoodsImageDAO v3 = new GoodsImageDAO();
            List<GoodsImage> listNoImage = v3.getNoImage();
            if (listNoImage.size() > 0) {
                paramHandler.sendMessage(paramHandler.obtainMessage(-2, listNoImage.size()));
                for (v5 = 0; v5 < listNoImage.size(); ++v5) {
                    GoodsImage goodsImage = listNoImage.get(v5);
                    v3.saveImage(goodsImage.getSerialid(), ssy.syn_QueryGoodsImage(goodsImage.getImagePath()),
                            goodsImage.getImagePath());
                    paramHandler.sendEmptyMessage(v5 + 1);
                }
            }
        }

        return true;
    }

    // 保存 HashMap 到 SQL
    public void saveToLocalDB(HashMap<String, String> map) {
        SQLiteDatabase localSQLiteDatabase = null;
        if (map.size() > 0 && map != null) {
            localSQLiteDatabase = new DBOpenHelper().getWritableDatabase();
            localSQLiteDatabase.beginTransaction();
            for (String key : map.keySet()) {
                localSQLiteDatabase.execSQL(map.get(key));
            }
            if (map.size() > 0) {
                // 必须写词句， 否则，查询不到插入的信息
                localSQLiteDatabase.setTransactionSuccessful();
            }
            // 结束
            localSQLiteDatabase.endTransaction();
            // 关闭
            localSQLiteDatabase.close();
        }
    }

    // 保存 List 到 SQL
    public boolean saveToLocalDB(List<HashMap<String, String>> paramList) {
        SQLiteDatabase localSQLiteDatabase = null;
        if ((paramList != null) && (paramList.size() > 0)) {
            localSQLiteDatabase = new DBOpenHelper().getWritableDatabase();
            // 开始
            localSQLiteDatabase.beginTransaction();
            try {
                for (int j = 0; j < paramList.size(); j++) {
                    if (paramList.get(j).get("sql").trim().length() > 0) {
                        String sql = (paramList.get(j)).get("sql").trim();
                        localSQLiteDatabase.execSQL(sql);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                // 必须写词句， 否则，查询不到插入的信息
                localSQLiteDatabase.setTransactionSuccessful();
                // 结束
                localSQLiteDatabase.endTransaction();
                // 关闭
                localSQLiteDatabase.close();
            }

        }

        return true;
    }

    public boolean executeCustomerGoodsAndDocUpdate(Handler arg20, List<String> arg21) {
        int v16 = 0;
        ServiceSynchronize v15 = new ServiceSynchronize(0);
        int v12 = v15.getPageSize();
        int v18 = arg21.size() / v12;
        int v17 = arg21.size() % v12 > 0 ? 1 : 0;
        int v13 = v18 + v17;
        ArrayList<RespCustomerGoodsAndDocPages> v10 = new ArrayList<>();
        if (v13 > 0) {
            int v6 = v12 - 1;
            if (v6 >= arg21.size()) {
                v6 = arg21.size() - 1;
            }
            String v4 = "";
            for (int i = 0; i <= v6; i++) {
                v4 = String.valueOf(v4) + ",\'" + arg21.get(i) + "\'";
            }
            if (v4.length() > 0) {
                v4 = v4.substring(1);
            }
            try {
                RespCustomerGoodsAndDocPages v14 = v15.syn_QueryCustomerGoodsAndDocPages(v4);
                if (v14 == null) {
                    arg20.sendMessage(arg20.obtainMessage(1, "客户信息同步失败，请重试"));
                    return false;
                }
                v14.setCustomers(v4);
                v16 += v14.getGoodsPages() + v14.getDocPages();
                v10.add(v14);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (v16 > 0) {
            for (int i = 0; i < v10.size(); i++) {
                RespCustomerGoodsAndDocPages v14_1 = v10.get(i);
                List<HashMap<String, String>> v8 = v15.syn_QueryCustomerGoodsRecords(v14_1.getCustomers(), i + 1);
                if (v8 != null) {
                    this.saveToLocalDB(v8);
                }
            }
            for (int i = 0; i < v10.size(); i++) {
                RespCustomerGoodsAndDocPages docPages = v10.get(i);
                List<HashMap<String, String>> v8 = v15.syn_QueryCustomerDocRecords(docPages.getCustomers(), i + 1);
                if (v8 != null) {
                    this.saveToLocalDB(v8);
                }
            }
        }
        return true;
    }
}
