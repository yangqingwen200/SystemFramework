package com.generic.listener;

import com.generic.cache.OrderPayingCache;
import com.generic.constant.InitDBConstant;
import com.generic.context.AppContext;
import com.generic.dao.GenericDao;
import com.generic.util.Asyncs;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Tomcat容器启动,初始化常量类{@link com.generic.constant.InitDBConstant InitDBConstant}属性 , 避免每次使用都从数据库中查询
 * @author Yang
 * @version v1.0
 * @date 2016年11月16日
 */
public class InitializationListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		GenericDao publicDao = AppContext.getBean("publicDao", GenericDao.class);
		List<Map<String, Object>> findBySqlMap = publicDao.findSqlListMap("select code, value from sys_code");

		//利用反射, 给InitDBConstant类中的所有属性赋值
		Class<?> cla = InitDBConstant.class;
		Field[] filed = cla.getFields();
		for (Field field : filed) {
			String fieldName = field.getName();
			for (Map<String, Object> map : findBySqlMap) {
				if (fieldName.equals(map.get("code"))) {
					try {
						field.set(cla, map.get("value"));
						break;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		//关闭线程池
		Asyncs.shutdownNow();

		//关闭定时任务
		OrderPayingCache.getInstance().shutdownNow();
	}
}
