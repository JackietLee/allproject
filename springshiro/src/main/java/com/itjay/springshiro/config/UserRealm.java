package com.itjay.springshiro.config;

import com.itjay.springshiro.entity.User;
import com.itjay.springshiro.service.PersonService;
import com.itjay.springshiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

public class UserRealm extends AuthorizingRealm {

    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        User user = (User) arg0.getPrimaryPrincipal();
//        List<Role> roles = roleService.listRolesByUserId(user.getId());
//        for (Role role : roles) {
//            authorizationInfo.addRole(role.getRole());
//            List<Permission> permissions = permissionService.listPermissionsByRoleId(role.getId());
//            for (Permission p : permissions) {
//                authorizationInfo.addStringPermission(p.getPermission());
//            }
//        }
//        return authorizationInfo;
        return null;
    }

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userSerivce;

//    @Autowired
//    private PersonService personService;

    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        System.out.println("执行认证逻辑");

        ///编写shiro判断逻辑，判断用户名和密码
        //1.判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken)arg0;
        User user=new User();
        user.setUserName(token.getUsername());
        user.setPassword(String.valueOf(token.getPassword()));
//        List<User> list=userSerivce.select(user);
        List<User> list=new ArrayList<>();
        if(list.size()==0){
            //用户名不存在
            return null;//shiro底层会抛出UnKnowAccountException
        }

        //2.判断密码
//        return new SimpleAuthenticationInfo(list.get(0),token.getPassword(), ByteSource.Util.bytes("sens"),token.getUsername());
        return new SimpleAuthenticationInfo(list.get(0),token.getPassword(),token.getUsername());
    }


}