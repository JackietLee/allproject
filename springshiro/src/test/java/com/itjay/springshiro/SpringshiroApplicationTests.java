package com.itjay.springshiro;

import com.itjay.springshiro.dao.UserDao;
import com.itjay.springshiro.entity.User;
import com.itjay.springshiro.mq.Producer;
import com.itjay.springshiro.mq.Publisher;
import com.itjay.springshiro.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

@SpringBootTest
class SpringshiroApplicationTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private Producer producer;

    @Autowired
    private Publisher publisher;

    @Autowired
    private PersonService personService;
    @Test
    void contextLoads() {
//        User user = new User();
//        user.setPassword("123");
//        user.setUserName("jay");
//        List<User> allByUserName = personService.find(user.getUserName());
//        System.out.println(allByUserName);
        for (int i = 0; i < 10; i++) {
            producer.sendMail("myqueue", "hello"+i);
        }
//        producer.sendMail("myqueue","111");
        publisher.senddirectMail("orange","orange");
        publisher.senddirectMail("black","black");
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("no-dependence.xml");
        SimpleBean simpleBean = (SimpleBean)context.getBean("simpleBean");
        System.out.println(simpleBean.getClass().getTypeName());
        simpleBean.sayHello("青石路");
    }

}
