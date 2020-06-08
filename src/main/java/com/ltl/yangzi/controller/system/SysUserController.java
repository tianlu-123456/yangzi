package com.ltl.yangzi.controller.system;

import com.ltl.yangzi.common.R;
import com.ltl.yangzi.common.annotion.SysLog;
import com.ltl.yangzi.common.form.PasswordForm;
import com.ltl.yangzi.common.validator.Assert;
import com.ltl.yangzi.common.validator.ValidatorUtils;
import com.ltl.yangzi.entity.SysUserEntity;
import com.ltl.yangzi.service.SysUserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.apache.xmlbeans.impl.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 系统用户
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
  @Autowired
  private SysUserServiceImpl sysUserService;

  /**
   * 通过姓名查询用户id
   */
  @RequestMapping("/infoByName")
  public R infoByName(@RequestBody SysUserEntity sysUserEntity) {
    return R.ok().put("data", sysUserService.getList(sysUserEntity));
  }

  /**
   * 添加用户
   */
  public R String(@RequestBody SysUserEntity sysUserEntity) {
    boolean add = sysUserService.add(sysUserEntity);
    return null;
  }

  /**
   * 获取登录的用户信息
   */
  @GetMapping("/info")
  public R info(HttpServletRequest request){
    //得到存储好的session user值
    Subject subject = SecurityUtils.getSubject();
    SysUserEntity user = (SysUserEntity) subject.getSession();
    return R.ok().put("user", user);
  }

  /**
   * 修改登录用户名密码
   */
  @SysLog("修改密码")
  @PostMapping("/password")
  public R password(HttpServletRequest request, @RequestBody PasswordForm form){
    Assert.isBlank(form.getNewPassword(), "新密码不能为空");
    //sha加密
    String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
    //sha256加密
    String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();
    Subject subject = SecurityUtils.getSubject();
    SysUserEntity user = (SysUserEntity) subject.getSession().getAttribute("User");
    boolean flag = sysUserService.updatePassword(user.getUserId(), password, newPassword)
    if (!flag){
      return R.error("原密码不正确");
    }
    return R.ok();
  }

  /**
   * 用户信息 true
   */
  @GetMapping("/infoById")
  public R info(@RequestParam Integer userId) {
    SysUserEntity user = sysUserService.getInfoById(userId);
    return R.ok().put("user", user);
  }

  /**
   * session中获取user
   */
  @GetMapping("getUserName")
  public R getImgById() {
    Subject subject = SecurityUtils.getSubject();
    SysUserEntity user = (SysUserEntity) subject.getSession().getAttribute("User");
    return R.ok().put("data", user);
  }

  /**
   * 保存用户
   */
  @PostMapping("/save")
  public R save(HttpServletRequest request, @RequestBody SysUserEntity user) {
    HashMap<String, Object> returnMap = new HashMap<>();
    //验证用户名是否存在
    String userName = sysUserService.getUserName(user.getUserName());
    if ( userName == null) {
      //验证手机号
      String phone = sysUserService.getPhone(user.getMobile());
      if ( phone ==null) {
        sysUserService.insert(user);
        return R.ok().put("data", user.getUserId());
      } else {
        return R.error(500, "手机号码已经被注册过");
      }
    } else {
      return R.error(500, "该用户名已被注册");
    }
  }

  /**
   * 修改个人信息
   */
  @SysLog("修改个人信息")
  @PostMapping("updatebyid")
  public R updatebyid(HttpServletRequest request, @RequestBody SysUserEntity user) {
    ValidatorUtils.validateEntity(user, UpdateGroup.class);
  }
}
