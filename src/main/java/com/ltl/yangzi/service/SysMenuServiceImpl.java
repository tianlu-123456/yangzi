package com.ltl.yangzi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltl.yangzi.common.utils.Constant;
import com.ltl.yangzi.dao.SysMenuDao;
import com.ltl.yangzi.entity.SysMenuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> {
  @Autowired
  private SysUserServiceImpl sysUserService;
  @Autowired
  private SysMenuServiceImpl sysMenuService;


  public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
    List<SysMenuEntity> menuList = queryListParentId(parentId);
    if(menuIdList == null){
      return menuList;
    }

    List<SysMenuEntity> userMenuList = new ArrayList<>();
    for(SysMenuEntity menu : menuList){
      if(menuIdList.contains(menu.getMenuId())){
        userMenuList.add(menu);
      }
    }
    return userMenuList;
  }


  public List<SysMenuEntity> queryListParentId(Long parentId) {
    return baseMapper.queryListParentId(parentId);
  }


  public List<SysMenuEntity> queryNotButtonList() {
    return baseMapper.queryNotButtonList();
  }


  public List<SysMenuEntity> getUserMenuList(Integer userId) {
    //系统管理员，拥有最高权限
    if(userId == Constant.SUPER_ADMIN ){
      return getAllMenuList(null);
    }


    //用户菜单列表
    List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
    return getAllMenuList(menuIdList);
  }

  public void delete(Long menuId){
    //删除菜单
    this.removeById(menuId);
  }

  /**
   * 获取所有菜单列表
   */
  private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList){
    //查询根菜单列表
    List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
    //递归获取子菜单
    getMenuTreeList(menuList, menuIdList);

    return menuList;
  }

  /**
   * 递归
   */
  private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList){
    List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();

    for(SysMenuEntity entity : menuList){
      //目录
      if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
        entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
      }
      subMenuList.add(entity);
    }

    return subMenuList;
  }

  public List<SysMenuEntity> selectLastId() {
    return baseMapper.selectLastId();
  }

  public List<SysMenuEntity> selectlist(){
    QueryWrapper<SysMenuEntity> queryWrapper=new QueryWrapper<>();
    queryWrapper.orderByAsc("order_num");
    return baseMapper.selectList(queryWrapper);}
}
