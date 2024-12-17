package com.microspring.aop.annotation;

import com.microspring.aop.autoproxy.AnnotationAwareAspectJAutoProxyCreator;
import com.microspring.ioc.bean.annotation.Import;

import java.lang.annotation.*;

/**
 * @author czf
 * @Date 2020/5/12 5:57 下午
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AnnotationAwareAspectJAutoProxyCreator.class)
public @interface EnableAspectJAutoProxy {
}
