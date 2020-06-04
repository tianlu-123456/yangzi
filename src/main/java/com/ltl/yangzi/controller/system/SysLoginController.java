package com.ltl.yangzi.controller.system;

import com.alibaba.fastjson.JSON;
import com.ltl.yangzi.common.R;
import com.ltl.yangzi.common.utils.RedisUtils;
import com.ltl.yangzi.entity.SysUserEntity;
import com.ltl.yangzi.service.SysUserServiceImpl;
import com.ltl.yangzi.service.SysUserTokenServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 登录相关
 */
@RestController
public class SysLoginController extends AbstractController {
  @Autowired
  private SysUserServiceImpl sysUserService;
  @Autowired
  private SysUserTokenServiceImpl sysUserTokenService;
  @Autowired
  private RedisUtils redisUtils;

  /**
   * 登录
   */
  @PostMapping("/sys/login")
  public Map<String, Object> login(HttpServletRequest request, @RequestBody String requestBody) {

    //1.requestBody转化为JSON格式的map
    Map<String, String> map = JSON.parseObject(requestBody, Map.class);

    //2.从map获取username和password
    String username = map.get("username");
    String password = map.get("password");

    //3.通过username查找对应的user
    SysUserEntity user = sysUserService.queryByUserName(username);

    //4.账号不存在、密码错误
    if (user == null || !user.getPassword().equals(new Sha256Hash(password,user.getSalt()).toHex())){
      return R.error("账号或密码不正确");
    }

    //5.更新最后登录时间
    user.setLastLoginTime(new Date());

    //6.更新user(sUS)
    sysUserService.updateById(user);

    //7.user存到session中
    Subject subject = SecurityUtils.getSubject();
    subject.getSession().setAttribute("user",user);

    //8.生成token，并保存到数据库

    R r = sysUserTokenService.createToken(user.getUserId());
    r.put("user",user);
    r.put("data",20000);

    //9.返回结果
    return r;

  }

  /**
   * 登出
   */
  @GetMapping("/sys/test")
  public void test(){
    SysUserEntity user = new SysUserEntity();
    user.setUserName("admin");
    user.setLoginName("admin");
    redisUtils.set(String.valueOf(user.getUserName()),user);
    System.out.println(redisUtils.get("admin"));
  }
}
