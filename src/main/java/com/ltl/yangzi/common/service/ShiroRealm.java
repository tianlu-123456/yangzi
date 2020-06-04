package com.ltl.yangzi.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ltl.yangzi.YangziApplication;
import com.ltl.yangzi.entity.SysUserEntity;
import com.ltl.yangzi.service.ShiroServiceImpl;
import com.ltl.yangzi.service.SysMenuServiceImpl;
import com.ltl.yangzi.service.SysUserServiceImpl;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {
  private static Logger log = LoggerFactory.getLogger(YangziApplication.class);
  /**
   * 禁止登录标志
   */
  private static final int FORBID_LOGIN = 0;

  @Autowired
  private SysUserServiceImpl service;
  @Autowired
  private SysMenuServiceImpl sysMenuService;
  @Autowired
  private ShiroServiceImpl shiroService;
  /**
   * 授权
   *
   * @param principalCollection
   * @return
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) throws ShiroException {
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
   * 认证
   *
   * @param authenticationToken
   * @return
   * @throws AuthenticationException
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
    UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
    // 校验用户名密码
    SysUserEntity user = service.findByLoginName(token.getUsername());
    return null;

  }
}
