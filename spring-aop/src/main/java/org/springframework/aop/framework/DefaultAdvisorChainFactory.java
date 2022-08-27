/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.aop.framework;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aopalliance.intercept.Interceptor;
import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.aop.Advisor;
import org.springframework.aop.IntroductionAdvisor;
import org.springframework.aop.IntroductionAwareMethodMatcher;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.adapter.AdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import org.springframework.lang.Nullable;

/**
 * A simple but definitive way of working out an advice chain for a Method,
 * given an {@link Advised} object. Always rebuilds each advice chain;
 * caching can be provided by subclasses.
 *
 * @author Juergen Hoeller
 * @author Rod Johnson
 * @author Adrian Colyer
 * @since 2.0.3
 */
@SuppressWarnings("serial")
public class DefaultAdvisorChainFactory implements AdvisorChainFactory, Serializable {

	@Override
	public List<Object> getInterceptorsAndDynamicInterceptionAdvice(
			Advised config, Method method, @Nullable Class<?> targetClass) {

		// This is somewhat tricky... We have to process introductions first,
		// but we need to preserve order in the ultimate list.
		AdvisorAdapterRegistry registry = GlobalAdvisorAdapterRegistry.getInstance();
		Advisor[] advisors = config.getAdvisors();
		List<Object> interceptorList = new ArrayList<>(advisors.length);
		Class<?> actualClass = (targetClass != null ? targetClass : method.getDeclaringClass());
		Boolean hasIntroductions = null;

		// 遍历所有的 Advisor，这里的 Advisor 是 ProxyFactory 中保存的
		for (Advisor advisor : advisors) {
			// 我们这里的Advisor 都是 PointcutAdvisor 所以这里只分析该内容
			/******* 1. PointcutAdvisor 顾问类型的处理 *******/
			// 如果是切点顾问
			if (advisor instanceof PointcutAdvisor) {
				// Add it conditionally.
				PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
				// 如果经过 预过滤 || 当前顾问 匹配当前类
				if (config.isPreFiltered() || pointcutAdvisor.getPointcut().getClassFilter().matches(actualClass)) {
					// 判断当前方法是否匹配
					MethodMatcher mm = pointcutAdvisor.getPointcut().getMethodMatcher();
					boolean match;
					if (mm instanceof IntroductionAwareMethodMatcher) {
						if (hasIntroductions == null) {
							hasIntroductions = hasMatchingIntroductions(advisors, actualClass);
						}
						match = ((IntroductionAwareMethodMatcher) mm).matches(method, actualClass, hasIntroductions);
					}
					else {
						match = mm.matches(method, actualClass);
					}
					// todo 把增强器转成 拦截器，如果是通知方法 如果代理规则与当前类匹配
					// 如果方法也匹配，则认为当前顾问适用于当前方法
					if (match) {
						// todo 进行转化注册
						// 获取顾问中的 方法拦截器。这里会将部分不合适的类型转换为合适的拦截器
						MethodInterceptor[] interceptors = registry.getInterceptors(advisor);
						// runtime 为 true， 表明是动态调用，即每次调用都需要执行一次判断。
						if (mm.isRuntime()) {
							// Creating a new object instance in the getInterceptors() method
							// isn't a problem as we normally cache created chains.
							// 动态调用将 拦截器包装成 InterceptorAndDynamicMethodMatcher
							for (MethodInterceptor interceptor : interceptors) {
								// 封装成 InterceptorAndDynamicMethodMatcher
								interceptorList.add(new InterceptorAndDynamicMethodMatcher(interceptor, mm));
							}
						}
						else {
							// 否则直接添加
							interceptorList.addAll(Arrays.asList(interceptors));
						}
					}
				}
			}
			else if (advisor instanceof IntroductionAdvisor) {
				/******* 2. IntroductionAdvisor 顾问类型的处理 *******/
				// 如果是引介顾问类型
				IntroductionAdvisor ia = (IntroductionAdvisor) advisor;
				// 预过滤 || 调用类匹配
				if (config.isPreFiltered() || ia.getClassFilter().matches(actualClass)) {
					// 直接添加
					Interceptor[] interceptors = registry.getInterceptors(advisor);
					interceptorList.addAll(Arrays.asList(interceptors));
				}
			}
			else {
				/******* 3. 其他顾问类型的处理 *******/
				// 直接添加
				Interceptor[] interceptors = registry.getInterceptors(advisor);
				interceptorList.addAll(Arrays.asList(interceptors));
			}
		}
		// 返回最终的拦截器集合
		return interceptorList;
	}

	/**
	 * Determine whether the Advisors contain matching introductions.
	 */
	private static boolean hasMatchingIntroductions(Advisor[] advisors, Class<?> actualClass) {
		for (Advisor advisor : advisors) {
			if (advisor instanceof IntroductionAdvisor) {
				IntroductionAdvisor ia = (IntroductionAdvisor) advisor;
				if (ia.getClassFilter().matches(actualClass)) {
					return true;
				}
			}
		}
		return false;
	}

}
