package com.itjay.springshiro.service.impl;

import com.itjay.springshiro.entity.User;
import com.itjay.springshiro.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService1Impl implements UserService {
    @Override
    public List<User> select(User user) {
        return null;
    }

    @Override
    public List<User> find(String user) {
        return null;
    }
}
