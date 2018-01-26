package com.system.common;

import com.generic.constant.SysConstant;
import com.generic.exception.MyException;
import com.generic.util.Page;
import com.generic.util.core.BL3Utils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseWebAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	protected static final Logger LOG = LoggerFactory.getLogger(BaseWebAction.class);
	protected int page; // 当前页数
	protected int rows; // 一页多少行
	protected String sort; // 排序字段
	protected String order; // 升序asc 或者 降序 desc
	protected Page<?> currentpage;
	protected boolean flag = true;
	protected List<Object> fuzzySearch = new ArrayList<Object>();  //用来装精确查询的关键字
	protected JSONObject json = new JSONObject();
	protected static final String OPERATION_SUCCESS = "operationSuccess";
	protected static final String OPERATION_FORWARD = "operationForward";

	protected void printJson() {
		try {
			json.accumulate("message", flag);
			this.printString(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void printJson(JSONObject json) {
		try {
			this.json = json;
			this.printJson();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void checkException(Exception e) {
		flag = false;
		if (e instanceof MyException) {
			json.accumulate("errorMsg", e.getMessage());
		} else {
			LOG.error(BL3Utils.getErrorMessage(3), e);
			json.accumulate("errorMsg", SysConstant.ERROR_MESSAGE);
		}
	}

	protected void checkExceptionAndPrint(Exception e) {
		String message = "";
		if (e instanceof MyException) {
			message = e.getMessage();
		} else {
			LOG.error(BL3Utils.getErrorMessage(3), e);
			message = SysConstant.ERROR_MESSAGE;
		}
		this.printString("<script type=\"text/javascript\">alert('" + message + "')</script>");
	}

	protected void checkExceptionOrForward(Exception e) {
		if(isFowardPage) {
			this.checkExceptionAndPrint(e);
		} else {
			this.checkException(e);
		}
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public com.generic.util.Page<?> getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(com.generic.util.Page<?> currentpage) {
		this.currentpage = currentpage;
	}
}
