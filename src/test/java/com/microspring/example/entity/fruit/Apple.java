package com.microspring.example.entity.fruit;

import lombok.Data;
import com.microspring.ioc.bean.annotation.Component;
import com.microspring.ioc.bean.annotation.Value;

/**
 * IOC测试
 * @Author: czf
 * @Date: 2020/5/9 8:44
 */
@Data
@Component("apple")
public class Apple implements Fruit {
    private String name = "apple";
    @Value("red")
    private String color;
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getColor() {
        return color;
    }
}
