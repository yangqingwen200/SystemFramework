package com.generic.exception;

/**
 * 自定义异常
 * @author Administrator
 * @Time 2015-3-9 下午01:32:20
 */
public class MyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errorMessage;
	private Object[] paramValue;
	
	public MyException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public MyException(String errorMessage, Object... paramValue) {
		this.errorMessage = errorMessage;
		this.paramValue = paramValue;
	}


	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object[] getParamValue() {
		return paramValue;
	}

	public void setParamValue(Object[] paramValue) {
		this.paramValue = paramValue;
	}
}
