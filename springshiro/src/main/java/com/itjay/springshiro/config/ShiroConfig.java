package com.itjay.springshiro.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //添加Shiro内置过滤器
        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器
         *    常用的过滤器：
         *       anon: 无需认证（登录）可以访问
         *       authc: 必须认证才可以访问
         *       user: 如果使用rememberMe的功能可以直接访问
         *       perms： 该资源必须得到资源权限才可以访问
         *       role: 该资源必须得到角色权限才可以访问
         */
        Map<String,String> filterMap = new LinkedHashMap<String,String>();
        /*filterMap.put("/add", "authc");
        filterMap.put("/update", "authc");*/

        //放行login.html页面
        filterMap.put("/login", "anon");
        filterMap.put("/index.html", "anon");
        //放行静态资源文件和不用拦截的访问
        filterMap.put("/js/**", "anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/big/**", "anon");
        filterMap.put("/fonts/**", "anon");
        filterMap.put("/images/**", "anon");
        filterMap.put("/Products/**", "anon");
        filterMap.put("/imgs/**", "anon");
        filterMap.put("/comment/**", "anon");
        filterMap.put("/Product_Detailed.html", "anon");
        filterMap.put("/commodity/**", "anon");
        filterMap.put("/pack/**", "anon");
        filterMap.put("/type/**", "anon");
        filterMap.put("/detail/**", "anon");
        filterMap.put("/collect/selectBycId", "anon");
        filterMap.put("/small/**", "anon");
        filterMap.put("/user/select", "anon");
        //授权过滤器

        filterMap.put("/**", "authc");

        //修改调整的登录页面
        shiroFilterFactoryBean.setLoginUrl("/login.htm");
        //设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/login.htm");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);


        return shiroFilterFactoryBean;
    }


    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 创建Realm
     */
    @Bean(name="userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }
}
