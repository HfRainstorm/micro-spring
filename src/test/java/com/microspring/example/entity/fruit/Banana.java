package com.microspring.example.entity.fruit;

import lombok.Data;
import com.microspring.ioc.bean.annotation.Service;
import com.microspring.ioc.bean.annotation.Value;

/**
 * @Author: czf
 * @Date: 2020/5/29 2:53
 */
@Data
@Service("banana")
public class Banana implements Fruit {
    private String name = "banana";
    @Value("yellow")
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
