package com.castis.filecollector.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		
		// 분산 DAO 추가
//		BasicDataSourceDistributeDAOBeanFactory distributeBeanFactory = beanFactory.getBean(BasicDataSourceDistributeDAOBeanFactory.class);
//		distributeBeanFactory.addDistributeDAOBeans(beanFactory);
	}
}
