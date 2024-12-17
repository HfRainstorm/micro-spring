package com.microspring.example.entity.animal;

import com.microspring.ioc.bean.BeanScope;
import com.microspring.ioc.bean.annotation.Component;
import com.microspring.ioc.bean.annotation.Lazy;
import com.microspring.ioc.bean.annotation.Scope;

/**
 * @Author: czf
 * @Date: 2020/5/29 3:27
 */
@Component
@Lazy
@Scope(BeanScope.SINGLETON)
public class Monkey {
}
