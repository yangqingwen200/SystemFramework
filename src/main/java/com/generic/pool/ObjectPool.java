package com.generic.pool;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public final class ObjectPool {

	/**
	 * 默认的eventmap
	 */
	private static ConcurrentHashMap<String, String> EVENTS_DETAULT = new ConcurrentHashMap<>();

	private final static KeyedObjectPool<Object, String> EVENT_POOL;

	static {
		GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
		config.setMaxTotal(10240);
		config.setMaxIdlePerKey(128);
		config.setMaxWaitMillis(2 * 1000);
		config.setMaxTotalPerKey(1024);
		config.setMinIdlePerKey(10);
		config.setTestOnBorrow(true);
		config.setTestWhileIdle(true);
		EVENT_POOL = new GenericKeyedObjectPool<>(new EventKeyedPooledObjectFactory(), config);
	}

	public static String borrowEvent(List para) throws Exception {
		//如果没有,就会调用create方法创建一个
		return EVENT_POOL.borrowObject(para);
	}

	public static void returnEvent(String reqType, String event) {
		if (event != null) {
			try {
				EVENT_POOL.returnObject(reqType, event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static class EventKeyedPooledObjectFactory extends BaseKeyedPooledObjectFactory<Object, String> {

		@Override
		public String create(Object key) throws Exception {
			List list = (List) key;
			Object object = list.get(0);
			Object methodName = list.get(1);
			Class<?> objectClass = object.getClass();
			Method method = objectClass.getMethod(methodName + "");
			return key +  "";
		}

		@Override
		public PooledObject<String> wrap(String value) {
			return new DefaultPooledObject<>(value);
		}
	}
}
