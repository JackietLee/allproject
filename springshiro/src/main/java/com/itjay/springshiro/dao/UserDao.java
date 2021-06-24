package com.itjay.springshiro.dao;

import com.itjay.springshiro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao  extends JpaRepository<User,Integer>{
    List<User> findAllByUserName(String userName);
}
