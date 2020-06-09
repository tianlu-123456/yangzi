package com.ltl.yangzi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ltl.yangzi.dao.SysUserDao;
import com.ltl.yangzi.entity.SysUserEntity;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity>{
  @Resource
  private SysUserDao sysUserDao;

  /**
   * 通过用户名查找用户
   * @param username
   * @return
   */
  public SysUserEntity queryByUserName(String username) {
    return baseMapper.queryByUserName(username);
  }

  /**
   * 根据userId查询用户的所有菜单ID
   * @param userId
   * @return
   */
  public List<Long> queryAllMenuId(Integer userId) {
    return baseMapper.queryAllMenuId(userId);
  }

  /**
   * 注入权限和角色数据
   * @param username
   * @return
   */
  public SysUserEntity findByLoginName(String username) {
    SysUserEntity user = new SysUserEntity();
    user.setUserName(username);
    //注入权限和角色数据
    QueryWrapper<SysUserEntity> userEntity = new QueryWrapper<>();
    userEntity.setEntity(user);
    return this.getOne(userEntity);
  }

  public List<SysUserEntity> queryPage(SysUserEntity sysUserEntity) {
    return sysUserDao.all(sysUserEntity);
  }

  /*通过userId查看用户信息*/
  public SysUserEntity getInfoById(Integer userId) {
    return sysUserDao.getInfoById(userId);
  }

  /*删除角色*/
  public int delUser(Integer userId) {
    return sysUserDao.delUser(userId);
  }

  public int updatePassword(String passwordSalt, Integer userId, String password, String salt) {
    return sysUserDao.updatePassword(passwordSalt, userId, password, salt);
  }

  /*通过姓名查询用户id*/
  public List<SysUserEntity> getList(SysUserEntity sysUserEntity) {
    return sysUserDao.getList(sysUserEntity);
  }

  /*添加用户*/
  public boolean add(SysUserEntity user) {
    boolean result = super.save(user);
    return result;
  }

  /*更新密码*/
  public boolean updatePassword(Integer userId, String password, String newPassword) {
    SysUserEntity userEntity = new SysUserEntity();
    userEntity.setPassword(newPassword);
    userEntity.setUnencryptedPassword(password);
    return this.update(userEntity,
        new QueryWrapper<SysUserEntity>().
            eq("user_id", userId).
            eq("password", password));
  }

  /*查看用户名是否可用*/
  public String getUserName(String userName) {
    return sysUserDao.getUserName(userName);
  }

  /*验证手机号*/
  public String getPhone(String mobile) {
    return sysUserDao.getPhone(mobile);
  }

  /*添加user*/
  @Transactional( rollbackFor = Exception.class)
  public boolean insert(SysUserEntity user) {
    user.setCreateDate(new Date());
    //sha256加密
    String salt = RandomStringUtils.randomAlphanumeric(20);
    user.setUnencryptedPassword(user.getPassword());
    user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
    user.setSalt(salt);
    Subject subject = SecurityUtils.getSubject();
    SysUserEntity loginuser = (SysUserEntity) subject.getSession().getAttribute("User");
    user.setCreateBy(loginuser.getUserId());
    //客户默认角色
    this.save(user);
    return true;
  }

  /**
   * 更新角色
   */
  @Transactional
  public void update(SysUserEntity user) {
    if (StringUtils.isBlank(user.getPassword())) {
      user.setPassword(null);
    } else {
      user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
    }
    this.updateById(user);
  }

  /**
   * 删除用户
   */
  public void deleteBatch(Integer[] userId) {
    this.removeByIds(Arrays.asList(userId));
  }

  /**
   * 查看所有日志接收人
   */
  public List<SysUserEntity> getRecvied() {
    return sysUserDao.getRecvied();
  }
}
