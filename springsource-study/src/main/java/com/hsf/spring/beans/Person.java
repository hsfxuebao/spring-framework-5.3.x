package com.hsf.spring.beans;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

/**
 * @author hsfxuebao
 * Created on 2022-06-28
 */
@Component
public class Person {

	private String name;

//	@Autowired  依赖的组件是多实例的时候不能使用@Autowired
	private Cat cat;

	@Lookup // 去容器中找，@Bean的这种方式注册的Person @Lookup不生效
	public Cat getCat() {
		return cat;
	}

	public void setCat(Cat cat) {
		this.cat = cat;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				'}';
	}
}
