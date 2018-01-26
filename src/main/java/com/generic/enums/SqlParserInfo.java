package com.generic.enums;

import com.generic.constant.SQLConstant;

/**
 * sql信息的枚举类
 * @author Yang
 * @version v1.0
 * @date 2016年12月6日
 */
public enum SqlParserInfo {
	
	FIND_WEB_USER_WEB(1000, SQLConstant.WEB_FIND_WEB_USER),
	FIND_PERMISSION_WEB(1001, SQLConstant.WEB_FIND_PERMISSION),
	FIND_APP_USER_WEB(1002, SQLConstant.WEB_FIND_APP_USER),
	FIND_DRIVINGSCHOOL_WEB(1003, SQLConstant.WEB_FIND_DRIVINGSCHOOL),
	;

	private final Integer sqlCode;
	private final String msg;
	
	private SqlParserInfo(Integer sqlCode, String msg) {
		this.sqlCode = sqlCode;
		this.msg = msg;
	}

	public Integer getSqlCode() {
		return sqlCode;
	}

	public String getMsg() {
		return msg;
	}

}
