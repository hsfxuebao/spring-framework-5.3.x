package com.hsf.spring.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


/**
 * 自定义监听器
 */
@Component
public class DemoListener implements ApplicationListener<DemoEvent> {


    @Override
    public void onApplicationEvent(DemoEvent event) {
    	System.out.println("DemoListener onApplicationEvent run");
        event.printMsg();
    }
}
