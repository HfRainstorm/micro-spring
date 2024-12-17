package com.microspring.example.entity.fruit;

/**
 * @Author: czf
 * @Date: 2020/5/29 2:54
 */

import lombok.Data;
import com.microspring.ioc.bean.annotation.Controller;

/**
 * @Author: czf
 * @Date: 2020/5/29 2:53
 */
@Data
@Controller("orange")
public class Orange implements Fruit {
    private String name = "orange";
    private String color = "orange";
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getColor() {
        return color;
    }
}

