package com.generic.exception;

import com.generic.enums.MsgExcInfo;

/**
 * 自定义Exception, 用来检查条件是否满足, 否则抛出此异常
 * @author Yang
 * @version v1.0
 * @date 2016年12月6日
 */
public class ValidateException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private MsgExcInfo msginfo;
	private Object[] paramValue;

	public ValidateException(MsgExcInfo msginfo) {
		this.msginfo = msginfo;
	}
	
	public ValidateException(MsgExcInfo msginfo, Object... paramValue) {
		this.msginfo = msginfo;
		this.paramValue = paramValue;
	}

	public MsgExcInfo getMsginfo() {
		return msginfo;
	}

	public Object[] getParamValue() {
		return paramValue;
	}

}
