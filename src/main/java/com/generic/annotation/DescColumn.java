package com.generic.annotation;

import com.system.bean.system.SysMenu;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来做日志用到<br>
 * 在实体bean属性get方法上面, 标识此属性, 范例{@link SysMenu SysMenu}中的属性get方法
 * @author Yang
 * @version v1.0
 * @date 2016年12月12日
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface DescColumn {
	
	/**
	 * 列的描述信息
	 * @return
	 * @author Yang
	 * @version v1.0
	 * @date 2016年12月10日
	 */
	String value();
}
