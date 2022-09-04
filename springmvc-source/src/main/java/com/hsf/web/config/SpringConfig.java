package com.hsf.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * @author hsfxuebao
 * Created on 2022-08-28
 */

/**
 * Spring不扫描controller组件、
 * AOP咋实现的，事务 看Spring容器
 */
@Configuration
@ComponentScan(value = "com.hsf.web",excludeFilters = {
		@ComponentScan.Filter(type= FilterType.ANNOTATION,value = Controller.class)
})
public class SpringConfig {
	// Spring父容器
}
