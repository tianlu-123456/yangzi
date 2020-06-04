package com.ltl.yangzi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ltl.yangzi.entity.SysMenuEntity;
import com.ltl.yangzi.entity.SysUserEntity;
import com.ltl.yangzi.entity.SysUserMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface SysUserMenuDao extends BaseMapper<SysUserMenu> {

  /*获取字段信息和别名*/
  List<Map<String,Object>> selectSearchBy();

  /*查询出所有可选的权限*/
  List<SysMenuEntity> permsList();

  /*查看用户权限*/
  Set<SysUserEntity> getUserPermissions(Integer userId);

  /*删除用户所有权限*/
  int delAll(Integer userId);

  /*添加菜单目录至导航栏*/
  int addMenu(@Param("userId") Integer userId, @Param("menuId") Long menuId);

  /*查询这个菜单的父级菜单*/
  Long getParentId(Long menuId);

  /*获取角色与菜单对应关系 列表*/
  List<SysUserMenu> getList(SysUserMenu sysUserMenu);

  /*添加菜单目录至导航栏*/
  int addMenus(@Param("userId") Integer userId,@Param("parentId") Long parentId);

}
