package com.immo.libcomm.http;


/**
 * @author Administrator
 * @content
 * @date 2017/11/29
 */

public class BaseUrl {
    //基础url
    private static String URL = "https://api.no1im.com";
//    private static String URL = "http://112.30.35.208:5001";
//    private static String URL = "http://192.168.60.137:9000";
//    public static String URL = "http://112.30.35.208:9010";

    //背景色
    public final static String BACKGROUND = "/base/app/bg";
    //验证码
    public final static String AUTOCODE = "/msg/pin/send";
    //获取验证码
    public final static String SENDSMS = "/msg/sms/send";
    //验证码-验证
    public final static String CHECKSMS = "/msg/sms/verify";
    //三方绑定
    public final static String BINDRTHREE = "/user/applet/mobile/bind";
    //注册
    public final static String REGISTER = "/user/reg";
    //登录-
    public final static String LOGIN = "/user/login";
    //忘记密码
    public final static String FORGETPS = "/user/pwd/reset";
    //修改密码
    public final static String CHANGEPS = "/user/pwd/modify";
    //商品详情页
    public final static String GOODDETAIL = "/online/goods/basis";
    //商品推荐
    public final static String REFERRALS = "/online/goods/referrals";
    //首页banner
    public final static String HOMEPAGE_BANNER = "/base/online/banner/list";
    //首页接口
    public final static String WINNER_HOMEPAGE_BANNER = "/base/winner/banner/list";

    //首页class
    public final static String HOMEPAGE_CLASS = "/base/online/cat/list";
    //赢家首页class
    public final static String WINNER_HOMEPAGE_CLASS = "/base/winner/cat/list";
    //商城快报
    public final static String HOMEPAGE_NOTICE = "/base/notice/list";
    //winner新闻
    public final static String WINNER_HOMEPAGE_NOTICE = "/base/winner/news";
    //热销市场
    public final static String HOMEPAGE_HOTMARK = "/base/hotmarket";
    //抢购
    public final static String FLASHBUY = "/shoppingFlash/activeList";
    //人气爆款
    public final static String HOMEPAGE_POPULAR = "/online/goods/recommend";
    //h5
    public final static String H5 = "/online/goods/h5";
    //商品评价
    public final static String GOODSEVA = "/online/goods/eval";
    //商品规格
    public final static String GOODSSPC = "/online/goods/selectGoodsSku";
    //商品收藏
    public final static String COLLECT = "/online/goods/collect";
    //取消
    public final static String CANCELCOLLECT = "/online/goods/cancelCollect";
    //店铺信息
    public final static String STOREMSG = "/online/store/basis";
    //店铺收藏
    public final static String STORECOLLECT = "/online/store/note";
    //取消
    public final static String STORECANCELCOLLECT = "/online/store/cancelNote";
    //店主推荐
    public final static String BOSSREFECH = "/online/store/goods/referrals";
    //热销商品
    public final static String HOTSALE = "/online/store/goods/hot";
    //全部商品
    public final static String ALLGOODS = "/online/store/goods/all";
    //新品上市
    public final static String NEWGOODDS = "/online/store/goods/latest";
    //宝贝分类
    public final static String GOODSCLASS = "/online/store/goods/cat";
    //加入购物车
    public final static String ADDCAR = "/online/cart/add";
    //购物车列表
    public final static String CARLIST = "/online/cart/list";
    //首页请购
    public final static String HOMEQIANGOU = "/shoppingFlash/aList";
    //Paypal支付
    public final static String PAYPALBASE = "/paypal/order/base";
    //Paypal支付结果查询
    public final static String PAYPALSEAERCH = "/paypal/order/check";
    //退款原因
    public final static String RETURNMONERREASON = "/order/return/reasons";
    //购物车删除
    public final static String CARDEl = "/online/cart/del";
    //购物车修改
    public final static String CARCHANGE = "/online/cart/modify";
    //购物车结算
    public final static String CARCALC = "/online/cart/calc";
    //购物车计算运费
    public final static String SHIPFEE = "/online/cart/transfee";
    //店铺列表
    public final static String STORELIST = "/online/store/search";
    //商品列表  全站搜索
    public final static String GoodList = "/online/goods/search";
    //分类
    public final static String CLASSLIST = "/base/cat/cascade";
    //分类
    public final static String COUPONCLASSLIST = "/coupons/types";
    //地址列表
    public final static String ADDRESSLIST = "/member/delivery/address/list";
    //三级联动
    public final static String ADDRESSTHREE = "/base/area/cascade";
    //新增地址
    public final static String ADDRESSADD = "/member/delivery/address/add";
    //删除地址
    public final static String ADDRESSDEL = "/member/delivery/address/del";
    //默认地址
    public final static String ADDRESSDEFAULT = "/member/delivery/address/default";
    //编辑地址
    public final static String EDITADDR = "/member/delivery/address/modify";
    //提交订单
    public final static String SUBMITORDER = "/online/order/commit";
    //订单列表
    public final static String ORDERLIST = "/online/order/list";
    //订单列表
    public final static String REFUNDLIST = "/online/order/refundlist";
    //退款详情
    public final static String REFUNDMONEYDETAIL = "/online/order/refunddetail";
    //申诉详情
    public final static String REFUNDSHELLDETAIL = "/online/order/appealDetail";
    //订单取消
    public final static String ORDERCANCLE = "/online/order/cancel";
    //支付订单
    public final static String ORDERPAY = "/online/order/pay";
    //订单删除
    public final static String ORDERDEL = "/online/order/del";
    //提醒发货
    public final static String ORDERHASTE = "/online/order/haste";
    //确认收货
    public final static String ORDERCONFIR = "/online/order/confirm";
    //查看物流
    public final static String ORDERSHIP = "/online/order/ecview";
    //交易成功的推荐
    public final static String ORDERREF = "/online/order/goods/interest";
    //交易成功的评价
    public final static String ORDEREVA = "/online/order/eval";
    //订单详情
    public final static String ORDERDETAIL = "/online/order/detail";
    //支付
    public final static String ORDERWXPAY = "/pay/order/WxAppPay";
    //  商城支付
    public final static String SHOPPAY = "/pay/bill/instant";
    //支付宝支付
    public final static String ZHIFUBAO = "/pay/order/alipay";
    //立即购买
    public final static String TOBUY = "/online/cart/instance";
    //尾款支付
    public final static String ENDTOBUY = "/online/cart/calcTail";
    //撤销退款
    public final static String CANCLORDERCACLE = "/online/order/ccancel";
    public final static String CANCLORDERORDER = "/order/return/cancel";

    //申请退货
    public final static String RETURNBEAN = "/online/cart/instance";
    //退出登录
    public final static String LOGINOUT = "/user/logout";
    //银行卡列表
    public final static String BANKLIST = "/member/bcard/list";
    //银行卡增加
    public final static String ADDBANK = "/member/bcard/add";
    //银行卡删除
    public final static String DELBANK = "/member/bcard/delete";
    //线上商品
    public final static String COLLECTLIST = "/record/collection/online/goods";
    //线下商品
    public final static String COLLECTLISTONLINE = "/record/collection/offline/package";
    //线上商家
    public final static String COLLECTLISTSTORE = "/record/collection/online/store";
    //线下商家
    public final static String COLLECTLISTONLINESTORE = "/record/collection/offline/shangjia";
    //商品删除
    public final static String COLLECTDEL = "/record/collection/del";
    //浏览记录线上商品
    public final static String BROWSINGLIST = "/record/browsing/online/goods";
    //启动页配置
    public final static String STARTURL = "/base/appstart";
    //浏览记录线下商家
    public final static String BROWSING_ONLINE_SHANGJIA = "/record/browsing/offline/shangjia";
    //清空足迹记录
    public final static String BROWSING_CLEAR = "/record/browsing/clear";
    //直接邀请人
    public final static String CENTER_DIRECTRELATIONS = "/user/center/directRelations";
    //间接邀请人
    public final static String CENTER_DINDIRECTRELATIONS = "/user/center/indirectRelations";
    //用户人脉上级邀请人信息
    public final static String CENTER_SUPERIORRELATIONS = "/user/center/superiorRelations";
    //修改头像
    public final static String CHANGEHAED = "/user/center/updateHeadimg";
    //帮助中心
    public final static String HELPLIST = "/base/article/list";
    //检查银行卡
    public final static String CHECKBANK = "/wallet/checkBlank";
    //自助申请
    public final static String SELFAPPLY = "/selfApply/submint";
    //自助申请列表
    public final static String SELFAPPLYLIST = "/selfApply/list";
    //自助申请列表
    public final static String SELFAPPLYDETAIL = "/selfApply/details";
    //线上是否有店铺
    public final static String LINEHASSTORE = "/user/center/storesettled/hasStore";
    //店铺申请信息
    public final static String STOREAPPLYMESSAGE = "/user/center/storesettled/getStore";
    //线上店铺类型
    public final static String LINEHASSTORESTYLE = "/user/center/storesettled/gradeDic";
    //线上经营类目
    public final static String LINEHASSTORECONTENT = "/user/center/storesettled/classDic";
    //文件上传
    public final static String FILEUP = "/upload/img";
    //文件上传
    public final static String LINESTOREUP = "/user/center/storesettled/saveStore";
    //  是否 实名认证  说明 obj -1：未提交 0：未审核 1：审核通过 2：审核不通过  -2:申请次数超限！
    public final static String ISCHECKNAME = "/user/center/authentication/has";
    //  实名认证
    public final static String IDCARDTRUE = "/user/center/authentication/save";
    //设置支付密码
    public final static String SETPAYPASSWORD = "/user/trade/setpw";
    //获取用户信息
    public final static String USERINFO = "/user/center/userrankinfo";
    //检查交易密码设置状态
    public final static String USERSTATE = "/user/trade/pwstat";
    //修改交易密码
    public final static String UPDATAUSER = "/user/trade/chpw";
    //设置用户手机号码
    public final static String USERMOBILESET = "/user/mobile/set";
    //检查用户手机号码设置状态  是否绑定手机号  0没有绑定 1 绑定手机  2 绑定邮箱 3.两个都绑定
    public final static String USERMOBILESTAT = "/user/mobile/stat";

    //微信支付检查
    public final static String PAYSUCCESS = "/pay/order/WxAppPayCheck";
    //支付宝成功
    public final static String ALIPAYSUCCESS = "/pay/order/query";
    //申请退款
    public final static String ORDERREFUND = "/online/order/applyrefund";
    //申请退货
    public final static String ORDERRETURNGOODS = "/order/return/commit";
    //快递公司列表
    public final static String ECLIST = "/base/ec/list";
    //申诉仲裁
    public final static String SHELLSUBMIT = "/online/order/submitAppeal";
    //取消申诉
    public final static String CANCLESHELLSUBMIT = "/online/order/cancelAppeal";
    //不申诉
    public final static String NoSHELLSUBMIT = "/online/order/noAppeal";
    //实名认证获取用户实名
    public final static String AUTHNAME = "/user/auth/name";
    //实名认证—查寻认证信息
    public final static String GETUSERDETAIL = "/user/center/authentication/get";
    //领券列表
    public final static String GETCOUNPONLIST = "/coupons/draw/list";
    //领券
    public final static String GETCOUNPON = "/coupons/draw/save";
    //手机和版本信息上传  mobileInfo  设备名称   version 手机code 版本  Android 版本
    public final static String PHONEINFOR = "/base/app/version/info";
    //余额概览（可用，冻结，累计，最近）
    public final static String BALANCESUMMARY = "/finance/balance/summary";
    //余额记录list  1 线上订单收入 2 线上订单支付 3 线下订单收入 4 线下订单支付 5 到店付收入 6 到店付 7 G转余额 8 提现申请 9 提现申请同意 10 提现申SS请拒绝
    public final static String BALANCELIST = "/finance/balance/list";
    //G概述
    public final static String VIRTUALSUMMARY = "/finance/virtual/summary";
    //G概述list
    public final static String VIRTUALLIST = "/finance/virtual/list";
    //G转出接口
    public final static String virtualtovirtual = "/finance/virtual/tovirtual";
    //G转出接口
    public final static String BEANTRUNOUT = "/userMdou/transfer";
    //G转入接口
    public final static String VIRTUALTOBALANCE = "/finance/virtual/tobalance";
    //我的余额 提现
    public final static String BALANCEENCASH = "/finance/balance/encash";
    //New 新的个人中心
    public final static String CENTERUSERCENTER = "/user/center/userCenter";
    //自助申请
    public final static String CENTERSELFAPPLICATION = "/user/center/selfApplication";
    //自助申请
    public final static String COLLAGETOCOO = "/online/cart/group";
    //银行卡背景信息
    public final static String BANKCARDBG = "/base/bankcard/bg";
    //退款详情
    public final static String RETURNDETAIL = "/order/return/detail";
    //退货物流
    public final static String RETURNLOGISTICS = "/order/return/logistics";
    //余额和G概述
    public final static String TOTALSUMMARY = "/finance/total/summary";
    //绑定邮箱
    public final static String EMAILSET = "/user/email/set";
    //设置用户名
    public final static String SETNAME = "/user/info/setname";
    //新的我的收益
    public final static String EARNINGSRECORDS = "/user/wallet/earnings/records";
    //交易记录查询
    public final static String TRADERECORDS = "/user/wallet/trade/records";
    //交易记录详情
    public final static String TRADERECORDSDETAIL = "/user/wallet/traderecords/detail";
    //协议 articleType  1.注册协议  2.入驻协议  3.抢购协议
    public final static String APPARTICLE = "/base/app/article";
    //个人中心券列表
    public final static String MYDRAWLIST = "/coupons/mydraw/list";
    //个人中心券列表  优惠券详情
    public final static String MYDRAWDETAIL = "/coupons/mydraw/detail";
    //抢购活动列表
    public final static String SHOPPINGFLASHEDITLIST = "/shoppingFlash/editList";
    //购物车优惠券列表
    public final static String CARTCOUPON = "/online/cart/coupon";

    //购物车拼团首页列表
    public final static String COLLAGEHOMELIST = "/group/acti/list";
    //购物车优惠券列表
    public final static String COLLAGEDETAIL = "/group/acti/detail";
    //拼团详情为你推荐
    public final static String COLLAGERECOM = "/group/acti/recommend";
    //拼团Banner
    public final static String COLLAGEBABNNER = "/group/acti/groupBanner";
    //三方登录
    public final static String THREELOGIN="/user/tpart/login";
    //优惠券Banner
    public final static String COUPONBNNER = "/coupons/couponBanner";
    //玩法介绍
    public final static String COLLAGEPLAY = "/group/acti/groupIntroduce";
    //预售协议
    public final static String PRESALE = "/preSaleSearch/presaleIntroduce";

    //合同列表
    public final static String CONTRACTLIST = "/contract/list";
    //合同详情
    public final static String CONTRACTDETAIL = "/contract/detail";
    //合同保存
    public final static String CONTRACTSAVE = "/contract/save";
    //拼团商品搜索接口
    public final static String ACTISEARCHGROUPGOODS = "/group/acti/searchGroupGoods";
    //支付方式
    public final static String PAYMENTLIST = "/payment/list";
    //重置交易密码 忘记密码找回使用
    public final static String TRADERESETPW = "/user/trade/resetpw";
    //脉预售首页
    public final static String PRESALESEARCHLIST = "/preSaleSearch/list";
    //脉预售首页 banner
    public final static String PRESALEBANNER = "/preSaleSearch/presaleBanner";
    //查询优惠券对应的商品
    public final static String COUPONSGOODSALL = "/coupons/goods/all";
    //脉预售 商品搜索
    public final static String presaleSearch = "/preSaleSearch/presaleSearch";
    //我的脉豆
    public final static String MYMDOULIST = "/userMdou/myMdou";
    //手机 和邮箱 验证码 验证   "to": 手机或邮箱 "way": 方式（1：手机 2：邮箱）"exists":是否存在（-1：不知道是否存在 0：不存在 1：存在）"code"：验证码
    public final static String PINVERIFY = "/msg/pin/verify";
    /**********************************************************线下部分*******************************************************************************/
    //线下分类
    public final static String LINEHOMECLASS = "/base/offline/cat/list";
    //线下商城快报
    public final static String LINE_HOMEPAGE_NOTICE = "/base/notice/list";
    //线下banner
    public final static String LINE_HOMEPAGE_BANNER = "/base/offline/banner/list";
    //店鋪詳情
    public final static String STOREDETAIL = "/offline/store/basis";
    public final static String SUBJECT = "/base/off/subject/list";

    //线下店铺收藏
    public final static String LINE_COLLECT_STORE = "/offline/store/collect";
    //线下店铺取消收藏
    public final static String LINE_CANCEL_COLLECT_STORE = "/offline/store/cancelCollect";
    public final static String LINE_GOODS_Hot = "/offline/store/goods/hot";
    public final static String LINE_GOODS_Detail = "/offline/goods/shangjiaPackageDetail";
    //获取入驻信息
    public final static String LINE_GET_STORE = "/user/center/shnagjiasettled/getStore";
    //线下用户是否有店铺
    public final static String LINE_EXIST_STORE = "/user/center/shnagjiasettled/hasStore";
    //保存实体商铺
    public final static String LINE_SAVE_STORE = "/user/center/shnagjiasettled/saveStore";
    //线下订单分页查询
    public final static String LINE_ORDER_LIST = "/offline/order/list";
    //线下店铺类型
    public final static String LINE_CLASS_DIC = "/user/center/shnagjiasettled/classDic";

    //线下订单详情
    public final static String LINE_ORDER_DEATIL = "/offline/order/deatil";
    //版本更新
    public final static String LINE_CHECK_VERSION = "/base/app/version/check";
    //线下商品
    public final static String LINE_GOODS_LIST = "/offline/goods/search";
    //热门分享
    public final static String SHAERBEAN = "/offline/store/share";
    //线下套餐推荐
    public final static String LINE_GOODS_REFERRALS = "/offline/goods/referrals";
    //线下单据删除
    public final static String LINE_ORDER_DELETE = "/offline/order/delete";
    //线下单据取消
    public final static String LINE_ORDER_CANCEL = "/offline/order/cancel";
    //线下退款
    public final static String LINE_ORDER_DRAWBACK = "/offline/order/refund";
    //线下店铺套餐评价列表
    public final static String LINE_STORE_EVAL_LIST = "/offline/store/eval/list";
    //线下订单评价
    public final static String LINE_STORE_EVAL_EVALUATE = "/offline/order/evaluate";
    //线下店铺分页搜索
    public final static String LINE_STORE_SEARCH = "/offline/store/search";
    //线下订单-提交订单
    public final static String LINE_ORDER_SUBMIT = "/offline/order/submit";
    //线下套餐收藏
    public final static String LINE_GOODS_COLLECTIONPACKAGE = "/offline/goods/collectionPackage";
    //线下套餐取消收藏
    public final static String LINE_GOODS_CANCELCOLLECTIONPACKAGE = "/offline/goods/cancelCollectionPackage";
    //线下分类列表
    public final static String LINE_OFFCLS_CASCADE = "/base/offcls/cascade";
    //获取市下面对应的县
    public final static String LINE_OFFCLS_AREA = "/base/off/area";

    public final static String LINE_OFFCLS_PAY = "/offline/order/pay";
    //线下 取消退款
    public final static String LINE_CANCEL_ORDER = "/offline/order/cancelrefund";
    public final static String LINE_PAY = "/offline/order/shangjipay";
    //     线下地址
    public final static String LINE_STORE = "http://offstore.no1im.com";
    //    线上地址
    public final static String OFFSTORE = "http://store.no1im.com/user/login.htm";

    /*******************************************************默认分享地址*******************************************************************/


    //二维码
    public final static String SHARE_NAME_NVITATIONCODEURL = "invitationCodeURL";
    //线下商品分享URL
    public final static String SHARE_NAME_SHAREOFFLINEGOODSURL = "shareOfflineGoodsURL";
    //线下店铺分享URL
    public final static String SHARE_NAME_SHAREOFFLINESTOREURL = "shareOfflineStoreURL";
    // 线上商品分享URL
    public final static String SHARE_NAME_SHAREONLINEGOODSURL = "shareOnlineGoodsURL";
    //线上店铺分享URL
    public final static String SHARE_NAME_SHAREONLINESTOREURL = "shareOnlineStoreURL";
    //数字房产证书URL
    public final static String DETAULT_NAME_SHOWDIGITALHOUSEURL = "showDigitalHouseURL";
    //im证书URL
    public final static String DETAULT_NAME_SHOWIMURL = "showImURL";
    //个人中心头部图片
    public final static String DETAULT_NAME_PERSONALCENTERIMAGEID = "personalCenterImageId";
    //个人中心默认 logo
    public final static String DETAULT_NAME_MEMBERICONID = "memberIconId";
    //颐脉流媒体直播地址
    public final static String DETAULT_NAME_BLANKURL = "blankUrl";
    //app首页 H5 url
    public final static String DETAULT_NAME_APPH5URL = "appH5URL";
    //TODO
    /**********************************************************赢家部分*******************************************************************************/
    //赢家个人中心详情
    public final static String WINNER_USERDETAIL = "/winneruser/user/userDetail";

//    public final static String WINNER_USERINFO = "/winneruser/user/userInfo";
    //关于赢家
    public final static String WINNER_ABOUT = "/winneruser/about/winner";
    //获取用户信息
    public final static String WINNER_USER_BASEINFO = "/winneruser/user/baseInfo";
    //修改昵称
    public final static String WINNER_UPDATE_USER_NICKNAME= "/winneruser/user/updateUserNickName";
    //修改个人地址
    public final static String WINNER_UPDATE_USER_DISTRICT= "/winneruser/user/updateUserDistrict";
    //赢家能力资料修改
    public final static String WINNER_CAPACITY_UPDATE= "/winneruser/capacity/update";
    //赢家能力资料 查询
    public final static String WINNER_USER_CAPACITY= "/winneruser/user/capacity";
    //获取分类
    public final static String WINNER_INTEGRAL_TYPE= "/winneruser/integral/type";
    //分享积分明细列表查询接口
    public final static String WINNER_INTEGRAL_LIST= "/winneruser/integral/list";
    //获取通兑积分类型列表
    public final static String WINNER_VIRTUAL_TYPE= "/winneruser/virtual/type";
    //获取通兑积分类内容列表
    public final static String WINNER_VIRTUAL_LIST= "/winneruser/virtual/list";
    //赢家通兑积分
    public final static String WINNER_VIRTUAL_INFO= "/winneruser/virtual/info";
    //获取赢家 商品销售、锁定赢家描述
    public final static String WINNER_SOURCE_DETAIL= "/winneruser/integral/source/detail";
    //赢家会员权益介绍
    public final static String WINNER_INTERESTS_DESC= "/user/interests/desc";
    //赢家升级资源描述
    public final static String WINNER_RESOURCE_DESC= "/user/resource/desc";


    public static String getUrl(String url) {

        return URL + url;
    }

    public static boolean setUrl(String urls) {
        URL = urls;
        return true;
    }

}
