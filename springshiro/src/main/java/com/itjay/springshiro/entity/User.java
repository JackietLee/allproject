package com.itjay.springshiro.entity;

import com.itjay.springshiro.SpringshiroApplication;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.SpringApplication;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(SpringshiroApplication.class, args);
    }
}
