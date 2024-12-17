package com.microspring.example.config;

import com.microspring.example.aspect.Log;
import com.microspring.example.aspect.printBeforeLogging;
import com.microspring.ioc.bean.annotation.Component;
import com.microspring.ioc.bean.annotation.ComponentScan;
import com.microspring.ioc.bean.annotation.ComponentScans;
import com.microspring.ioc.bean.annotation.Import;

/**
 * @author czf
 * @Date 2020/5/9 3:21 下午
 */
@ComponentScans({@ComponentScan("com.microspring.example.entity"),@ComponentScan("com.microspring.example.entity")})
@ComponentScan("com.microspring.example.entity")
@Import({Log.class,printBeforeLogging.class})
@Component
public class Config {
}
