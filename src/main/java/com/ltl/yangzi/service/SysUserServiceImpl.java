package com.ltl.yangzi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ltl.yangzi.dao.SysUserDao;
import com.ltl.yangzi.entity.SysUserEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
