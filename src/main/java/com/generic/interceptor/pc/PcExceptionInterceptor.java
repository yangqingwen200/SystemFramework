package com.generic.interceptor.pc;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yang
 * @version v1.0
 * @date 2017/8/1
 */
public class PcExceptionInterceptor implements Interceptor {

    protected static final Logger LOG = LoggerFactory.getLogger(PcExceptionInterceptor.class);

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    public String intercept(ActionInvocation actioninvocation) {
        try {
            String result = null; // Action的返回值
            // 运行被拦截的Action, 期间如果发生异常会被catch住
            result = actioninvocation.invoke();
            return result;
        } catch (Exception e) {
            HttpServletRequest request = ServletActionContext.getRequest();
            request.setAttribute("errorMessage", "Waring: " + e.getMessage());
            return "error";
        }
    }

}

