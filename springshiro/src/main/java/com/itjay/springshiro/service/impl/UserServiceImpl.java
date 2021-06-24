package com.itjay.springshiro.service.impl;

import com.itjay.springshiro.dao.UserDao;
import com.itjay.springshiro.entity.User;
import com.itjay.springshiro.service.PersonService;
import com.itjay.springshiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private PersonService personService;
    @Autowired
    private UserDao userDao;

    public UserServiceImpl(@Lazy PersonService personService){
        this.personService = personService;
    }

    @Override
    public List<User> select(User user) {
        return null;
    }

    @Override
    public List<User> find(String user) {
        return userDao.findAllByUserName(user);
    }
}
