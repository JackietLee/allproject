package com.itjay.springshiro;

import com.itjay.springshiro.dao.UserDao;
import com.itjay.springshiro.entity.User;
import com.itjay.springshiro.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringshiroApplicationTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PersonService personService;
    @Test
    void contextLoads() {
        User user = new User();
        user.setPassword("123");
        user.setUserName("jay");
        List<User> allByUserName = personService.find(user.getUserName());
        System.out.println(allByUserName);
    }

    public static void main(String[] args) {
        System.out.println(1543523453&127);
        System.out.println(1543523453%128);
    }

}
