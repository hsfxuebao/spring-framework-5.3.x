package com.hsf.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hsfxuebao
 * Created on 2022-09-05
 */
@Service
public class UserService {


	@Autowired
	private UserDao userDao;

	// 不自动注入，模拟空指针异常
	private UserInfoService userInfoService;


	@Transactional
	public void insertUser() {
		userDao.insert();
		System.out.println("插入完成");
		// 模拟空指针异常
		userInfoService.test();
	}

	/**
	 * 以下测试事务是否有效
	 */
	// 事务方法不是 public 方法不生效
	@Transactional
	protected void insertUser2() {
		userDao.insert();
		System.out.println("插入完成");
		// 模拟空指针异常
		userInfoService.test();
	}

	// 在不是事务方法中调用 事务的方法 事务不生效
	public void testTransactionValid() {
		System.out.println("testTransactionValid");
		insertUser();
	}
	// 解决办法，就是调用代理对象的事务方法，如下
	@Autowired  // Spring解决了循环依赖
	private UserService userServiceImpl;
	public void insertUser3() {
//		insertUser(); // 直接调用事务方式，insertUser()所有的设置是不生效的
		userServiceImpl.insertUser(); // 事务 设置全部生效
		this.insertUser();//不生效，只要不是代理对象都不会生效，SpringAOP默认没有设置暴露代理对象。
	}

	// 默认情况下只会在捕获了RuntimeException后Transactional注解才会生效，如果在代码中捕获了异常后未抛出，则Transactional失效：
	// 解决办法就是在捕获异常的时候 再次把异常抛出去
	@Transactional
	public void insertUser4() {
		userDao.insert();
		System.out.println("插入完成");
		try {

			// 模拟空指针异常
			userInfoService.test();
		} catch (RuntimeException e) {
			// 打印异常
//			throw new RuntimeException();
		}
	}
}
