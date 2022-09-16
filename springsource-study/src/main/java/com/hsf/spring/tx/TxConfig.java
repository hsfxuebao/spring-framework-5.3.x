package com.hsf.spring.tx;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author hsfxuebao
 * Created on 2022-09-05
 *
 * 声明式事务：
 * 1. 导入相关依赖
 * 		数据源，数据库驱动，Spring-jdbc模块
 * 2.配置数据源，JdbctTemplate(Spring提供的简化数据库操作的工具)操作数据
 * 3.给方法上标注@Transactional 表示当前方法是一个事务方法
 * 4.@EnableTransactionManagement 开启基于注解的事务管理功能
 * 		@EnableXXX
 * 5.配置事务管理器来控制事务 TransactionManager
 *
 *
 * 原理：
 * 1. @EnableTransactionManagement
 * 		利用TransactionManagementConfigurationSelector 给容器中导入2个组件
 * 			AutoProxyRegistrar
 * 			ProxyTransactionManagementConfiguration
 * 2.AutoProxyRegistrar：给容器中注册一个InfrastructureAdvisorAutoProxyCreator组件
 *		InfrastructureAdvisorAutoProxyCreator：
 *	         	(AOP:利用后置处理器机制在对象创建以后，包装对象。返回一个代理对象(增强器)，代理对象执行方法利用拦截器链进行调用)
 *
 * 3. ProxyTransactionManagementConfiguration:给容器中注册事务增强器
 *
 * 		3.1. 事务增强器要用事务注解的的信息，TransactionAttributeSource 解析事务的注解
 * 		3.2. 事务拦截器：TransactionInterceptor，保存了事务属性信息，事务管理器
 * 			他是一个MethodInterceptor，在目标方法执行的时候：
 * 				执行拦截器链：
 * 				事务拦截器：
 * 					3.2.1. 先获取事务相关的属性
 * 					3.2.2. 再获取PlatformTransactionManager 如果事先没有添加指定任务的transactionManager
 * 						最终会从容器中按照类型获取PlatformTransactionManager
 *					3.2.3. 执行目标方法：
 *						如果异常，获取到事务管理器，利用事务管理执行回滚操作
 *						如果正常，利用事务管理器，提交事务
 */
@ComponentScan("com.hsf.spring.tx")
@Configuration
@EnableTransactionManagement
public class TxConfig {

	@Bean
	public DataSource dataSource() throws Exception {

		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setUser("root");
		dataSource.setPassword("hsfxuebao");
		dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/spring_tx");
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() throws Exception {
		// Spring对@Configuration类会特殊处理，给容器中加组件的方法，多次调用都只是从容器中找组件
		return new JdbcTemplate(dataSource());
	}

	// 注册事务管理器
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		return new DataSourceTransactionManager(dataSource());
	}

}
