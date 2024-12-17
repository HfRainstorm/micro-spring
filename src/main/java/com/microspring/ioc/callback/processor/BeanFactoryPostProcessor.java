package com.microspring.ioc.callback.processor;

import com.microspring.ioc.container.BeanContainer;

/**
 * @author czf
 * @Date 2020/5/11 6:21 下午
 */
public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(BeanContainer beanContainer);
}
