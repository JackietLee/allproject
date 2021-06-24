package net.pingfang.config.shiro;

import net.pingfang.realm.MyRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Filter;

/**
* @Author: cgf
* @Description: Shiro配置类
* @Date: Created in 2018/2/8 13:29
* @param 
*/
//@Configuration
public class ShiroConfig {

	/**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
       * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
      * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     *
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        /**
         * 常用过滤器：
         * anon:无需认证（登录）可以访问
         * authc:必须认证才可以访问
         * user:如果使用rememberMe的功能可以直接访问
         * perms:该资源必须得到资源权限才可以访问
         * role:该资源必须得到角色权限才可以访问
         */
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        shiroFilterFactoryBean.setLoginUrl("/user/logout");
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/logout");
        
        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
       
        //配置记住我或认证通过可以访问的地址(配置不会被拦截的链接 顺序判断)
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/user/loadMenuInfo", "anon");
        //数据服务接口不经过权限处理
         filterChainDefinitionMap.put("/dataService/**", "anon");
//        filterChainDefinitionMap.put("/qhdPdf/**", "anon");
        
//         filterChainDefinitionMap.put("/emCrane/**", "anon");
        
        
//        filterChainDefinitionMap.put("/work/**", "anon");
       // filterChainDefinitionMap.put("/testVesselBay/**", "anon");
       // filterChainDefinitionMap.put("/testVesselContainer/**", "anon");
        
        
        
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources", "anon");
        filterChainDefinitionMap.put("/swagger-resources/configuration/security", "anon");
        filterChainDefinitionMap.put("/swagger-resources/configuration/ui", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/webjars/springfox-swagger-ui/**", "anon");
        	

        LinkedHashMap<String, Filter> filtsMap=new LinkedHashMap<String, Filter>();
        filtsMap.put("authc",new ShiroFormAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filtsMap);
       
        // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
       
         filterChainDefinitionMap.put("/**/**", "authc");
         // 使用该过滤器过滤所有的链接
        // filterChainDefinitionMap.put("/**","custom");
        
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
}

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myRealm());
        //注入记住我管理器;
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
     * 
     * @return
     */
    @Bean
    public MyRealm myRealm() {
    	MyRealm myRealm = new MyRealm();
        return myRealm;
    }
    /**
     * Shiro生命周期处理器
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * 不要使用 DefaultAdvisorAutoProxyCreator 会出现二次代理的问题，这里不详述
     * @return
     */
   /* @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }*/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * cookie对象;
     * 记住密码实现起来也是比较简单的，主要看下是如何实现的。
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        System.out.println("ShiroConfiguration.rememberMeCookie()");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * cookie管理对象;
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        System.out.println("ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }
    
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager() {
    	DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    	// 设置session过期时间3600000s,设置的最大时间，正负都可以，为负数时表示永不超时。
    	sessionManager.setGlobalSessionTimeout(3600000000L);
    	return sessionManager;
    }

}
