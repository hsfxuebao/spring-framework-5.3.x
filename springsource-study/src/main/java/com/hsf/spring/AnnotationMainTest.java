package com.hsf.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hsf.spring.aop.HelloService;
import com.hsf.spring.config.MainConfig;
import com.hsf.spring.listener.DemoEvent;
import com.hsf.spring.listener.MessageEvent;
import com.hsf.spring.tx.TxConfig;
import com.hsf.spring.tx.UserService;

/**
 * @author hsfxuebao
 * Created on 2022-07-23
 */
public class AnnotationMainTest {


	public static void main(String[] args) {

		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(TxConfig.class);
		// 事务
		UserService userService = context.getBean(UserService.class);
		// public 方法事务生效
//		userService.insertUser();
		userService.testTransactionValid();


//		AnnotationConfigApplicationContext context =
//				new AnnotationConfigApplicationContext(MainConfig.class);
//		//AOP,原理测试
//		HelloService helloService = context.getBean(HelloService.class);
////		helloService.sayHello("zhangsan");
//		helloService.sayHello2("zhangsan2");
//		System.out.println();

//		//测试事件监听器
//		DemoEvent demoEvent = new DemoEvent("", "world");
//		context.publishEvent(demoEvent);
//		context.publishEvent(new MessageEvent(""));


		// FactoryBean测试
//		Object hello = context.getBean("helloFactory");
//		System.out.println(hello);
//		Object helloFactory = context.getBean("&helloFactory");
//		System.out.println(helloFactory);

//		// 使用@Bean 注入实例
//		Object person = context.getBean(Person.class);
//		System.out.println(person);
//
//		// 使用@Import 注入组件，参见Cat类
//		final String[] beanDefinitionNames = context.getBeanDefinitionNames();
//		for (String beanDefinitionName : beanDefinitionNames) {
//			System.out.println(beanDefinitionName);
//		}

//		// 在Cat上 使用@Scope 说明注入的是单例 还是原生  对象
//		Cat bean1 = context.getBean(Cat.class);
//		Cat bean2 = context.getBean(Cat.class);
//		System.out.println(bean1 == bean2);  //false

//		// 单例组件Person想依赖非单例组件 Cat，可以使用@Lookup注解
//		final Person bean3 = context.getBean(Person.class);
//		final Cat cat1 = bean3.getCat();
//		final Person bean4 = context.getBean(Person.class);
//		final Cat cat2 = bean4.getCat();
//		// 如果在Person中使用@Autowired中注入，是true;
//		// 如果在person中getCat中使用@Lookup注解，getCat的时候会直接从容器中找，由于Cat是原生的，每次get都是不同的实例，返回false
//		System.out.println(cat1 == cat2);  // false


		// Aware 接口通过@Autowired 注入ApplicationContext
//		PersonAware bean = context.getBean(PersonAware.class);
//		System.out.println(bean.getContext());
		// Aware 通过实现 ApplicationContextAware 接口


	}

}
