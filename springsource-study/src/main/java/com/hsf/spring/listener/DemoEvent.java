package com.hsf.spring.listener;

import java.io.Serializable;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义监听事件,需要实现序列化接口
 */
public class DemoEvent  extends ApplicationEvent implements Serializable {

	private static final long serialVersionUID = 0L;

    private String msg;
    public DemoEvent(Object source) {
        super(source);
    }

    public DemoEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public void printMsg(){
        System.out.println("msg = " + msg);
    }
}
