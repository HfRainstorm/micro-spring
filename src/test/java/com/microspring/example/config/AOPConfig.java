package com.microspring.example.config;

import com.microspring.aop.annotation.EnableAspectJAutoProxy;
import com.microspring.ioc.bean.annotation.Component;
import com.microspring.ioc.bean.annotation.ComponentScan;

/**
 * @Author: czf
 * @Date: 2020/5/29 13:45
 */
@Component
@EnableAspectJAutoProxy
@ComponentScan("com.microspring.example.aspect")
public class AOPConfig {
}
