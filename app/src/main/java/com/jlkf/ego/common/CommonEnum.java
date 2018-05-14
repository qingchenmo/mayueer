package com.jlkf.ego.common;

/**
 *  * 返回结果 错误码枚举
 * @author  邓超桂
 * @date 创建时间：2017-07-06.
 */
public class CommonEnum {
    /** 成功 **/
    public final static int SUCCESS = 0;
    /** 数据没有签名（仅当启用签名模式时才有） **/
    public final static int DATA_NO_SIGN=100;
    /** 签名错误（仅当启用签名模式时才有） **/
    public final static int DATA_SIGN_ERROR=101;
    /** 没有传递token **/
    public final static int WITHOUT_TOKEN=102;
    /** token错误 **/
    public final static int TOKEN_ERROR=103;
    /** token过期 **/
    public final static int TOKEN_OUTTIME=104;
    /**服务器异常 **/
    public final static int SERVER_ERROR=105;
    /**  缺少参数（必填的参数没有填） **/
    public final static int PARAMS_ERROR=106;
}
