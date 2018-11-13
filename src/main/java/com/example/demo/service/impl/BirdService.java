package com.example.demo.service.impl;

import com.example.demo.service.AnimalDao;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by Raytine on 2018/11/4.
 */
@Component
@Order(3)
public class BirdService  implements AnimalDao{

    @Override
    public void sing() {
        System.out.println("i  am  a bird");
    }
}
