package com.itjay.springshiro.service;

import com.itjay.springshiro.entity.User;

import java.util.List;

public interface PersonService {
    public List<User> find(String user);
}
