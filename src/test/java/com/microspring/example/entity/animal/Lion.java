package com.microspring.example.entity.animal;

import lombok.Data;
import com.microspring.ioc.bean.annotation.AutoWired;
import com.microspring.ioc.bean.annotation.Component;

/**
 * @Author: czf
 * @Date: 2020/5/29 14:40
 */
@Component("lion")
@Data
public class Lion {
    @AutoWired
    private Tiger tiger;
}
