package com.generic.filter;

import org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 改变用户访问的请求路径
 * 
 * 如: login_User.do变成login_user_User.do(方法名_模块_Action类名.do形式)
 * 
 * @author Administrator
 *
 * 2015年10月12日 下午5:04:52
 */
public class ChangeAccessPathFilter extends StrutsPrepareAndExecuteFilter {

	public void doFilter(final ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		request = (HttpServletRequest) Proxy.newProxyInstance(HttpServletRequest.class.getClassLoader(), new Class[] { HttpServletRequest.class },
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						if ("getServletPath".equals(method.getName())) {
							String beforeUrl = method.invoke(req, args).toString(); //得到方法的返回值
							String[] urls = beforeUrl.split("_");
							
							//控制只改变*_*访问的路径
							if(urls.length == 2) {
								String str = urls[urls.length-1].substring(0, urls[urls.length-1].lastIndexOf(".")); //得到类名
								String low = str.substring(0, 1).toLowerCase(); //首字母小写
								String lowClass = low + str.substring(1, str.length());
								return urls[0] + "_" + lowClass + "_" + urls[1];
							}
						}
						return method.invoke(req, args);
					}
				}); 
		// 必须是代理后的请求对象
		chain.doFilter(request, res);
	}

}
