package com.hsf.spring.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author hsfxuebao
 * Created on 2022-08-24
 */
@Component
public class MessageEventTwoListener implements ApplicationListener<MessageEvent> {

	@Override
	public void onApplicationEvent(MessageEvent event) {
		System.out.println("MessageEventTwoListener onApplicationEvent run");
	}
}
