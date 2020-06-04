package com.ltl.yangzi.service;

import com.ltl.yangzi.dao.SysUserMenuDao;
import com.ltl.yangzi.entity.SysMenuEntity;
import com.ltl.yangzi.entity.SysUserEntity;
import com.ltl.yangzi.entity.SysUserMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色与菜单对应关系
 */
@Service
public class SysUserMenuService extends ServiceImpl<SysUserMenuDao, SysUserMenu>{
  @Resource
  private SysUserMenuDao sysUserMenuDao;

  /**
   * 获取角色与菜单对应关系
   * @return
   */
  public List<Map<String,Object>> getSearch() {
    List<Map<String, Object>> o = sysUserMenuDao.selectSearchBy();
    //遍历
    Iterator<Map<String, Object>> l = o.iterator();
    while (l.hasNext()){
      Map<String, Object> map = l.next();
      if ("编号".equals(map.get("column_comment"))){
        l.remove();
      }
      if ("删除标记".equals(map.get("column_comment"))){
        l.remove();
      }
      if ("备注信息".equals(map.get("column_comment"))){
        l.remove();
      }
      if ("更新时间".equals(map.get("column_comment"))){
        l.remove();
      }
      if ("更新者".equals(map.get("column_comment"))){
        l.remove();
      }
      if ("创建时间".equals(map.get("column_comment"))){
        l.remove();
      }
      if ("创建者".equals(map.get("column_comment"))){
        l.remove();
      }
    }
    return o;
  }

  /**
   * 查询出所有可选的权限
   * @return
   */
  public List<SysMenuEntity> permsList() {
    return sysUserMenuDao.permsList();
  }

  /**
   * 查看用户权限
   * @param userId
   * @return
   */
  public Set<SysUserEntity> getUserPermissions(Integer userId) {
    return sysUserMenuDao.getUserPermissions(userId);
  }

  /**
   * 删除用户所有权限
   * @param userId
   * @return
   */
  public int delAll(Integer userId) {
    return sysUserMenuDao.delAll(userId);
  }

  /**
   * 添加菜单目录至导航栏
   * @param menuId
   * @param userId
   * @return
   */
  public int addMenu(Long menuId, Integer userId) {
    return sysUserMenuDao.addMenu(userId, menuId);
  }

  /**
   *  查询这个菜单的父级菜单
   * @param menuId
   */
  public Long getParentId(Long menuId) {
    return sysUserMenuDao.getParentId(menuId);
  }

  /**
   * 获取角色与菜单对应关系 列表
   * @param sysUserMenu
   */
  public List<SysUserMenu> getList(SysUserMenu sysUserMenu){
    return sysUserMenuDao.getList(sysUserMenu);
  }

  public int addMenus(Long parentId, Integer userId) {
    return sysUserMenuDao.addMenus(userId, parentId);
  }
}
