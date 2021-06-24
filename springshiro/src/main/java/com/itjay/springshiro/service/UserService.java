package com.itjay.springshiro.service;

import com.itjay.springshiro.entity.User;

import java.util.List;

public interface UserService {
    List<User> select(User user);

    List<User> find(String user);
}
