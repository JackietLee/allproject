package com.itjay.springshiro.service.impl;

import com.itjay.springshiro.entity.User;
import com.itjay.springshiro.service.PersonService;
import com.itjay.springshiro.service.UserService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private UserService userService;

    public PersonServiceImpl(@Qualifier("userServiceImpl") UserService userService){
        this.userService = userService;
    }

    @Override
    public List<User> find(String user) {
        return userService.find(user);
    }
}
