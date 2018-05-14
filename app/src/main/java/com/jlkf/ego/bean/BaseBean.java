package com.jlkf.ego.bean;

import java.io.Serializable;

/**
 * 实体类基类
 * @author  邓超桂
 * @date 创建时间：2016年10月8日 下午4:04:10
 */
public class BaseBean implements Serializable{
	private static final long serialVersionUID = 1L;
	public int returnCode;
	public String errorMsg;
	public int getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}

