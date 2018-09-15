package com.sunwuyou.swymcx.utils;

import com.sunwuyou.swymcx.dao.GoodsPriceDAO;
import com.sunwuyou.swymcx.model.RespGoodsPriceEntity;

import java.util.List;

/**
 * Created by admin
 * 2018/9/15.
 * content
 */

public class DocUtils {
    /**
     * 查询商品价格体系
     *
     * @param goodsid
     * @return
     */
    public static List<RespGoodsPriceEntity> queryGoodsPriceList(String goodsid) {
        GoodsPriceDAO goodspricedao = new GoodsPriceDAO();

        return goodspricedao.queryPriceList(goodsid);
    }
}
