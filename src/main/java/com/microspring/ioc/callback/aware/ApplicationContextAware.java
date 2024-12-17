package com.microspring.ioc.callback.aware;

import com.microspring.ioc.context.ApplicationContext;

/**
 * @author czf
 * @Date 2020/10/4 3:35 下午
 */
public interface ApplicationContextAware extends Aware{
    void setApplicationContext(ApplicationContext app);
}
