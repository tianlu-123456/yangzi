package com.ltl.yangzi.common.oauth2;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ltl.yangzi.YangziApplication;
import com.ltl.yangzi.service.ShiroServiceImpl;
import com.ltl.yangzi.entity.SysUserEntity;
import com.ltl.yangzi.entity.SysUserTokenEntity;
import com.ltl.yangzi.service.SysUserTokenServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 认证
 *
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
    private static Logger log = LoggerFactory.getLogger(YangziApplication.class);
    @Autowired
    private ShiroServiceImpl shiroService;
    @Autowired
    private SysUserTokenServiceImpl sysUserTokenService;
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUserEntity user = (SysUserEntity)principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> permissions = shiroService.getUserPermissions(user.getUserId());
        authorizationInfo.addStringPermissions(permissions);
        Map<String, Object> map = new HashMap<>();
        Set<String> roles = new HashSet<>();

        Wrapper wrapper=new QueryWrapper();
        ((QueryWrapper) wrapper).eq("user_id",user.getUserId());
        Wrapper userRoleEntityWrapper=new QueryWrapper();
        authorizationInfo.addRoles(roles);

        log.info("用户"+user.getUserName()+"具有的角色:"+authorizationInfo.getRoles());
        log.info("用户"+user.getUserName()+"具有的权限："+authorizationInfo.getStringPermissions());

        return authorizationInfo;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        //根据accessToken，查询用户信息
        SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
        //token失效
        if(tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }
        Subject subject= SecurityUtils.getSubject();
        SysUserEntity sysUserEntity=(SysUserEntity) subject.getSession().getAttribute("User");
        if(sysUserEntity==null){
            throw new IncorrectCredentialsException("session失效，请重新登录");
        }
        //查询用户信息
        SysUserEntity user = shiroService.queryUser(tokenEntity.getUserId());
        Date now = new Date();
        //过期时间
        tokenEntity.setExpireTime(new Date(now.getTime() + 3600 * 1000 *2));
        sysUserTokenService.updateById(tokenEntity);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
    }
}
