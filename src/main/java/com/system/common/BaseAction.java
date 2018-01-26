package com.system.common;

import com.generic.constant.SysConstant;
import com.generic.redis.CacheRedisClient;
import com.generic.service.GenericService;
import com.generic.util.core.BL3Utils;
import com.generic.util.core.Dto;
import com.generic.util.core.WebUtils;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = 1L;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected PrintWriter out;
	protected Dto dto;
	protected boolean isFowardPage = false;
	protected boolean isGetRequest = false;

	protected CacheRedisClient cacheRedis;
	protected GenericService publicService;

	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
		this.response.setContentType("text/html; charset=utf-8");
		try {
			this.out = this.response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
		this.session = request.getSession();
		this.dto = WebUtils.getParamAsDto(request);
		this.isGetRequest = WebUtils.isGetRequest(request);
		BL3Utils.handleNullStr(dto);
		isFowardPage = dto.containsKey(SysConstant.FORWARD_PAGE);
	}

	protected void printString(String msg) {
		try {
			out.print(msg);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public HttpSession getSession() {
		return session;
	}

	public GenericService getPublicService() {
		return publicService;
	}

	public Dto getDto() {
		return dto;
	}

	public void setDto(Dto dto) {
		this.dto = dto;
	}

	@Autowired
	@Qualifier("publicService")
	public void setPublicService(GenericService publicService) {
		this.publicService = publicService;
	}

	public CacheRedisClient getCacheRedis() {
		return cacheRedis;
	}

	@Autowired
	@Qualifier("cacheRedisClient")
	public void setCacheRedis(CacheRedisClient cacheRedis) {
		this.cacheRedis = cacheRedis;
	}

}
