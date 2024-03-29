package com.hsf.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.hsf.spring.beans.Cat;
import com.hsf.spring.beans.Person;

/**
 * @author hsfxuebao
 * Created on 2022-07-23
 */
@Configuration
//@Import({Person.class}) // 导入某个bean,默认使用无参构造器创建对象
//@Import(value = {MainConfig.MyImportBeanDefinitionRegistrar.class})
@ComponentScan("com.hsf.spring")  // 默认扫描当前路径下的bean
@EnableAspectJAutoProxy
public class MainConfig {


	@Bean
	public Person getPerson() {
		Person person = new Person();
		person.setName("张三");
		return person;
	}


}
