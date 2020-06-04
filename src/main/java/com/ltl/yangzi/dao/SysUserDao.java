package com.ltl.yangzi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ltl.yangzi.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {

  /*根据用户名查找用户*/
  SysUserEntity queryByUserName(String username);

  /*根据userId查询用户的所有权限*/
  List<String> queryAllPerms(long userId);

  /*根据userId查询用户的所有权限*/
  List<String> queryUserPerm(long userId);

  /*根据userId查询用户的所有菜单ID*/
  List<Long> queryAllMenuId(Integer userId);

  /*获取用户列表*/
  List<SysUserEntity> all(SysUserEntity sysUserEntity);

  /*通过userId查看用户信息*/
  SysUserEntity getInfoById(Integer userId);

  /*删除角色*/
  int delUser(Integer userId);

  /*更新密码*/
  int updatePassword(@Param("password1") String passwordsalt, @Param("userId") Integer userId, @Param("password") String password, @Param("salt") String salt);
}
