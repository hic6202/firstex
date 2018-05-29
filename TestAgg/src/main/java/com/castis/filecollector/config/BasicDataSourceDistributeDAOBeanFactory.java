package com.castis.filecollector.config;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
public class BasicDataSourceDistributeDAOBeanFactory {
	
	@SuppressWarnings("unchecked")
	public void addDistributeDAOBeans(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		GenericXmlApplicationContext tempCtx = new GenericXmlApplicationContext(new FileSystemResource(System.getProperty("catalina.home") + "/conf/FileCollector/distributeDB-context.xml"));
		List<Map<String, String>> distributeDBInfoList = (List<Map<String, String>>) tempCtx.getBean("distributeDBInfoList");
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
		int idx = 1;
		for (Map<String, String> dbInfoMap : distributeDBInfoList) {
			try {
				// 1. DataSource 추가
				String dataSourceBeanName = "distributeDataSource_" + idx;
				BeanDefinition dataSourceBD = beanFactory.getBeanDefinition("importDataSource");
				GenericBeanDefinition distributeDataSourceBD = new GenericBeanDefinition(dataSourceBD);
				distributeDataSourceBD.getPropertyValues().add("url", dbInfoMap.get("url"));
				distributeDataSourceBD.getPropertyValues().add("username", dbInfoMap.get("username"));
				distributeDataSourceBD.getPropertyValues().add("password", dbInfoMap.get("password"));
				registry.registerBeanDefinition(dataSourceBeanName, distributeDataSourceBD);
				
				// 2. SessionFactory 추가
				String distributeSqlSessionFactoryBeanName = "distributeSqlSessionFactory_" + idx;
				BeanDefinition sessionFactoryBD = beanFactory.getBeanDefinition("importSqlSessionFactory");
				GenericBeanDefinition distributeSessionFactoryBD = new GenericBeanDefinition(sessionFactoryBD);
				distributeSessionFactoryBD.getPropertyValues().add("dataSource", new RuntimeBeanReference(dataSourceBeanName));
				registry.registerBeanDefinition(distributeSqlSessionFactoryBeanName, distributeSessionFactoryBD);
				
				// 3. CSVImportDAO 추가
				String distributeTempAggregationDAOName = "distributeCSVImportDAOMybatis_" + idx;
				BeanDefinition tempAggregationDAOBD = beanFactory.getBeanDefinition("CSVImportDAOMybatis");
				GenericBeanDefinition distributeBD = new GenericBeanDefinition(tempAggregationDAOBD);
				ConstructorArgumentValues ca = new ConstructorArgumentValues();
				ca.addGenericArgumentValue(new RuntimeBeanReference(distributeSqlSessionFactoryBeanName));
				distributeBD.setConstructorArgumentValues(ca);
 				registry.registerBeanDefinition(distributeTempAggregationDAOName, distributeBD);

				idx++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}