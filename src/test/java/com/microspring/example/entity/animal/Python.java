package com.microspring.example.entity.animal;

import com.microspring.ioc.bean.BeanScope;
import com.microspring.ioc.bean.annotation.Component;
import com.microspring.ioc.bean.annotation.Scope;

/**
 * @Author: czf
 * @Date: 2020/5/29 3:28
 */
@Component
@Scope(BeanScope.SINGLETON)
public class Python {
}
