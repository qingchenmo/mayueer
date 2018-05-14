package com.jlkf.ego.net;

/**
 * @autor zcs
 * @time 2017/8/19
 * @describe
 */

public class Urls {

    public static void setUrl(String url) {
        Urls.url = url;
    }

    public static String getUrl() {
        return url;
    }

    //    private static String url = "http://120.25.237.198:8380/myr-api/";   // 接 口地址
//    private static String url = "http://192.168.1.156:8111/myr-api/";   // 本地接口地址
//    public static String url = "http://52.28.215.17:8080/myr-api/";    //客户服务器
//    public static String url = "http://api.oubus.com/";    //客户服务器
    public static String url = "http://api.hi-ego.com/";    //正式服务器地址

    /**
     * 商品查询
     */
    public static final String getOitmViewByBrand = url + "oitmView/getOitmViewByBrand";
    /**
     * 批量下单
     */
    public static final String batchInsert = url + "shopCart/batchInsert";
    /**
     * 发送验证码
     */
    public static final String sendCode = url + "user/sendCode";
    /**
     * 注册
     */
    public static final String register = url + "user/register";
    /**
     * 登录user/
     */
    public static final String login = url + "user/login";
    /**
     * 修改密码/
     */
    public static final String forgetPassWord = url + "user/forgetPassWord";
    /**
     * 修改密码2
     */
    public static final String updatePassWord = url + "user/updatePassWord";
    /**
     * 引导页/
     */
    public static final String getPic = url + "guide/getPic";
    /**
     * 品牌列表/
     */
    public static final String getBrand = url + "ubrand/getBrand";
    /**
     * 推荐品牌
     */
    public static final String recommendBrand = url + "ubrand/recommendBrand";
    /**
     * 推荐品牌
     */
    public static final String recommendListOne = url + "oitmView/recommendListOne";
    /**
     * 购物车列表
     */
    public static final String list = url + "shopCart/list";
    /**
     * 删除购物车列表
     */
    public static final String delete = url + "shopCart/delete";
    /**
     * 编辑购物车
     */
    public static final String update = url + "shopCart/update";
    /**
     * 收货地址列表
     */
    public static final String addrlist = url + "address/addrlist";

    /**
     * 订单列表以及详情
     */
    public static final String orderList = url + "order/list";

    /**
     * 搜索订单
     */
    public static final String selectOrderBycode = url + "order/selectOrderBycode";
    /**
     * 新增用户地址
     */
    public static final String insert = url + "address/insert";
    /**
     * 修改用户地址
     */
    public static final String adressUpdate = url + "address/update";
    /**
     * 我的收藏
     */
    public static final String getMyColler = url + "ucoller/getMyColler";
    /**
     * 服务热线
     */
    public static final String getClerk = url + "user/getClerk";
    /**
     * 公司电话
     */
    public static final String phone = url + "company/list";
    /**
     * 删除订单
     */
    public static final String adress_delete = url + "address/delete";
    /**
     * 个人信息
     */
    public static final String getUserInfo = url + "user/getUserInfo";
    /**
     * 分类列表
     */
    public static final String itemgetList = url + "itemGroupView/getList";
    /**
     * 二级分类列表
     */
    public static final String getSecondList = url + "itemGroupView/getSecondList";
    /**
     * 新增购物车
     */
    public static final String shopInsert = url + "shopCart/insert";
    /**
     * 修改个人信息
     */
    public static final String updateUser = url + "/user/updateUser";
    /**
     * 获取消息
     */
    public static final String getMessage = url + "umessage/getMessage";
    /**
     * 消息中心
     */
    public static final String getMessageDetail = url + "umessage/getMessageDetail";
    /**
     * 删除消息
     */
    public static final String deleteMessage = url + "umessage/deleteMessage";
    /**
     * 选择国家
     */
    public static final String countr = url + "city/countr";
    /**
     * 选择州
     */
    public static final String province = url + "city/province";
    /**
     * 绑定手机号
     */
    public static final String boundPhone = url + "user/boundPhone";
    /**
     * 上传头像
     */
    public static final String pcphotoImg = url + "pcphoto/pcphotoImg";
    /**
     * 确认收货
     */
    public static final String confirmReceipt = url + "order/confirmReceipt";
    /**
     * 再次购买
     */
    public static final String buyAgain = url + "order/buyAgain";
    /**
     * 去结算
     */
    public static final String goSettlement = url + "order/goSettlement";
    /**
     * 取消订单
     */
    public static final String cancelOrder = url + "order/cancelOrder";
    /**
     * 修改订单
     */
    public static final String updateOrder = url + "order/updateOrder";
    /**
     * 收藏或取消收藏
     */
    public static final String coller = url + "ucoller/coller";
    /**
     * 支付方式
     */
    public static final String payType = url + "order/payType";
    /**
     * 运费查询
     */
    public static final String cityFreight = url + "order/cityFreight";

    /**
     * 修改默认收货地址
     */
    public static final String udefault = url + "address/udefault";

    /**
     * 设置已读/未读
     */
    public static final String read = url + "order/read";
    /**
     * 订单统计
     */
    public static final String count = url + "order/count";
    /**
     * 确认订单
     */
    public static final String confirmOrder = url + "order/confirmOrder";
    /**
     * 校验验证码
     */
    public static final String checkCode = url + "user/checkCode";
    /**
     * 绑定第三方
     */
    public static final String userBind = url + "user/userBind";
    /**
     * 解除绑定
     */
    public static final String unBind = url + "user/unbind";
    /**
     * 第三方登录
     */
    public static final String thirdLogin = url + "user/thirdLogin";
    /**
     * 设置消息已读
     */
    public static final String isRead = url + "umessage/isRead";
    /**
     * 实发商品清单
     */
    public static final String invoice = url + "order/invoice";
    /**
     * 首页消息未读
     */
    public static final String allRead = url + "umessage/allRead";
    /**
     * 商品详情
     */
    public static final String getOitmViewByCode = url + "oitmView/getOitmViewByCode";
    /**
     * 本类快选
     */
    public static final String getOitmBySecond = url + "oitmView/getOitmBySecond";
    /**
     * 推荐品牌
     */
    public static final String recommendListTwo = url + "oitmView/recommendListTwo";
}
