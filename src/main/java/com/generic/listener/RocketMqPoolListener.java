package com.generic.listener;

import com.generic.mq.MQConsumer;
import com.generic.mq.MQProducer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 在web.xml添加此监听器.
 */
public class RocketMqPoolListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//先订阅,再发布
		MQConsumer.startCustomer();
		MQProducer.startProducer();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		MQProducer.shutdownProducer();
		MQConsumer.shutdownCustomer();
	}
}
