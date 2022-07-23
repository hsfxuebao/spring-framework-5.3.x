package com.hsf.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hsf.spring.beans.Person;

/**
 * @author hsfxuebao
 * Created on 2022-06-28
 */
public class MainText {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context =
				new ClassPathXmlApplicationContext("beans.xml");

		Person bean = context.getBean(Person.class);
		System.out.println(bean);

	}
}
