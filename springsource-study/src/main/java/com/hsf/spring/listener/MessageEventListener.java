package com.hsf.spring.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author haoshaofei <haoshaofei@kuaishou.com>
 * Created on 2022-08-24
 */
@Component
public class MessageEventListener implements ApplicationListener<MessageEvent> {

	@Override
	public void onApplicationEvent(MessageEvent event) {
		System.out.println("MessageEventListener onApplicationEvent run");
	}
}
