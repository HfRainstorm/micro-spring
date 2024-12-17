package com.microspring.example.entity.human;

import lombok.Data;
import com.microspring.ioc.bean.BeanScope;
import com.microspring.ioc.bean.annotation.AutoWired;
import com.microspring.ioc.bean.annotation.Component;
import com.microspring.ioc.bean.annotation.Qualifier;
import com.microspring.ioc.bean.annotation.Scope;

/**
 * 循环依赖测试
 * @Author: czf
 * @Date: 2020/5/28 10:12
 */
@Component("xiaolang")
@Scope(BeanScope.SINGLETON)
@Data
public class Boy {
    private String name = "xiaolang";
    @AutoWired
    @Qualifier("sakura")
    private Girl girl;
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Boy{" +
                "name='" + name + '\'' +
                ", my hashCode=" + this.hashCode()+
                ", girl=" + girl.getName() +
                ", xiaolang's hashCode=" + girl.hashCode()+
                '}';
    }
}
