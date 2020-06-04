package com.ltl.yangzi.common.config;

import com.ltl.yangzi.common.oauth2.OAuth2Filter;
import com.ltl.yangzi.common.oauth2.OAuth2Realm;
import com.ltl.yangzi.common.persistence.RedisSessionDao;
import com.ltl.yangzi.common.service.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    private static final Logger log = LoggerFactory.getLogger(ShiroFilterFactoryBean.class);

    /**
     * 密码匹配器，设置匹配规则
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher matcher() {
      HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME);
      // 加密次数
      // matcher.setHashIterations(EncryptionUtils.HASH_ITERATION);
      return matcher;
    }

    /**
     * 自定义session缓存机制
     *
     * @return
     */
    @Bean
    public RedisSessionDao redisSessionDao() {
      return new RedisSessionDao();
    }

    /**
     * 自定义shiro realm
     *
     * @return
     */
    @Bean
    public ShiroRealm hperingRealm(HashedCredentialsMatcher matcher) {
      ShiroRealm hperingRealm = new ShiroRealm();
      hperingRealm.setCredentialsMatcher(matcher);
      return hperingRealm;
    }


    /**
     * 安全管理器
     *
     * @param realm
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(OAuth2Realm oAuth2Realm, ShiroRealm realm, DefaultWebSessionManager sessionManager) {
      DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
      securityManager.setRealm(oAuth2Realm);
      securityManager.setSessionManager(sessionManager);
      return securityManager;
    }

    /**
     * 重写session管理器，并注入自定义session缓存机制
     *
     * @param sessionDao
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager(RedisSessionDao sessionDao) {
      DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
      // sessionManager.setSessionDAO(sessionDao);
      sessionManager.setGlobalSessionTimeout(60 * 1000 * 60 * 12);
      sessionManager.setSessionValidationSchedulerEnabled(true);
      sessionManager.setSessionIdUrlRewritingEnabled(false);
      sessionManager.setSessionDAO(sessionDao);
//        /** 此注释代码 就是将JSESSIONID变成自定义名称 WEBJSESSIONID**/
//        sessionManager.setSessionIdCookieEnabled(true);
//        SimpleCookie cookie = new SimpleCookie("WEBJSESSIONID");
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(60 * 60 * 1000);
//        sessionManager.setSessionIdCookie(cookie);
      return sessionManager;
    }

    /**
     * shiro 授权过滤器
     *
     * @param securityManager
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
      ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
      shiroFilter.setSecurityManager(securityManager);
      //oauth过滤
      Map<String, Filter> filters = new HashMap<>();
      filters.put("oauth2", new OAuth2Filter());
      shiroFilter.setFilters(filters);
      Map<String, String> filterMap = new LinkedHashMap<>();
      filterMap.put("/druid/**", "anon");
      filterMap.put("/sys/login", "anon");
      filterMap.put("/sys/test", "anon");
      filterMap.put("/captcha.jpg", "anon");
      filterMap.put("/**/img/**", "anon");
      filterMap.put("/sysusermenu/**", "anon");
      filterMap.put("/sms/**", "anon");
      filterMap.put("/sys/user/**", "anon");
      filterMap.put("/weChat/**", "anon");
      filterMap.put("/service/**", "anon");
      filterMap.put("/basicinformationhousecredit/**", "anon");
      filterMap.put("/houseloan/**", "anon");
      filterMap.put("/homepage/**", "anon");
      filterMap.put("/housingcreditinformation/**", "anon");
      filterMap.put("/specialassets/**", "anon");
      filterMap.put("/specialdatabasicinformation/**", "anon");
      filterMap.put("/specialdatainformation/**", "anon");
      filterMap.put("/vistrecord/**", "anon");

//
      filterMap.put("/badassets/**", "anon");
      filterMap.put("/appletusermanagement/**","anon");
      filterMap.put("/adminaccountmanagement/**", "anon");
      filterMap.put("/HomePageBack/**", "anon");
      filterMap.put("/BackHouseLoan/**", "anon");
      filterMap.put("/BackBadAssets/**", "anon");
      filterMap.put("/BackSpecialAssets/**", "anon");
      filterMap.put("/BackVistRecord/**", "anon");
      filterMap.put("/Seek/**", "anon");
//
      //测试接口,添加匿名访问,测试完成删除
      filterMap.put("/sys/menu/**", "anon");
      filterMap.put("/sys/login/**", "anon");
      filterMap.put("/log/**", "anon");

      filterMap.put("/**", "oauth2");
      shiroFilter.setFilterChainDefinitionMap(filterMap);
      return shiroFilter;
    }

    /**
     * 解决shiro注解不生效
     * 注：shiro目前没有正式发布对springboot的支持，等支持版发布后再重新调整
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
      DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
      defaultAAP.setProxyTargetClass(true);
      return defaultAAP;
    }

    /**
     * 开启shiro注解
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
      AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
      advisor.setSecurityManager(securityManager);
      return advisor;
    }
    /**
     * 通知器
     *
     */
//    @Bean("lifecycleBeanPostProcessor")
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }


}